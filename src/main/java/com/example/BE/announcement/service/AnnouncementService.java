package com.example.BE.announcement.service;

import com.example.BE.announcement.domain.AnnouncementEntity;
import com.example.BE.announcement.domain.dto.AnnouncementCreateRequest;
import com.example.BE.announcement.domain.dto.AnnouncementResponse;
import com.example.BE.announcement.domain.dto.AnnouncementUpdateRequest;
import com.example.BE.announcement.repository.AnnouncementRepository;
import com.example.BE.feedback.domain.FeedbackEntity;
import com.example.BE.feedback.repository.FeedbackRepository;
import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.AnnouncementErrorCode;
import com.example.BE.global.exception.errorCode.FeedbackErrorCode;
import com.example.BE.global.exception.errorCode.StoreErrorCode;
import com.example.BE.store.domain.StoreEntity;
import com.example.BE.store.repository.StoreRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final StoreRepository storeRepository;
    private final FeedbackRepository feedbackRepository;

    // 개선사항 생성
    @Transactional
    public AnnouncementResponse createAnnouncement(Long userId, AnnouncementCreateRequest request) {
        validateStoreOwner(userId, request.storeId());

        FeedbackEntity feedback = findFeedbackByIdOrThrow(request.feedbackId());
        validateFeedbackBelongsToStore(feedback, request.storeId());

        AnnouncementEntity newAnnouncement = AnnouncementEntity.from(request);
        announcementRepository.save(newAnnouncement);

        return AnnouncementResponse.of(newAnnouncement, feedback.getContent());
    }

    // 개선사항 단건 조회
    public AnnouncementResponse getAnnouncementById(Long announcementId) {
        AnnouncementEntity announcement = findByIdOrThrow(announcementId);
        FeedbackEntity feedback = findFeedbackByIdOrThrow(announcement.getFeedbackId());
        return AnnouncementResponse.of(announcement, feedback.getContent());
    }

    // 개선사항 리스트 조회
    public List<AnnouncementResponse> getAnnouncementsByStoreId(Long storeId) {
        List<AnnouncementEntity> announcements = announcementRepository.findByStoreId(storeId);

        // 조회할 feedbackId 목록
        List<Long> feedbackIds = announcements.stream()
            .map(AnnouncementEntity::getFeedbackId)
            .toList();

        // 이후 feedbackId에 따라 빠른 조회를 위해 Map<>으로 변환
        Map<Long, String> feedbackMap = feedbackRepository.findAllByIdIn(feedbackIds)
            .stream()
            .collect(Collectors.toMap(FeedbackEntity::getId, FeedbackEntity::getContent));

        // feedbackMap에서 feedbackId와 매핑되는 feedbackContent들을 하나씩 AnnouncementResponse에 담음.
        return announcements.stream()
            .map(announcement -> AnnouncementResponse.of(announcement,
                feedbackMap.get(announcement.getFeedbackId())))
            .collect(Collectors.toList());
    }

    // 개선사항 수정
    @Transactional
    public AnnouncementResponse updateAnnouncement(Long userId, Long announcementId,
        AnnouncementUpdateRequest request) {
        FeedbackEntity feedback = findFeedbackByIdOrThrow(request.feedbackId());

        AnnouncementEntity announcement = findByIdOrThrow(announcementId);
        validateStoreOwner(userId, announcement.getStoreId());
        validateFeedbackBelongsToStore(feedback, announcement.getStoreId());

        announcement.update(request.feedbackId(), request.description());
        return AnnouncementResponse.of(announcement, feedback.getContent());
    }

    // 개선사항 삭제
    @Transactional
    public void deleteAnnouncement(Long userId, Long announcementId) {
        AnnouncementEntity announcement = findByIdOrThrow(announcementId);
        validateStoreOwner(userId, announcement.getStoreId());

        announcementRepository.deleteById(announcementId);
    }


    // private methods
    private AnnouncementEntity findByIdOrThrow(Long announcementId) {
        return announcementRepository.findById(announcementId)
            .orElseThrow(() -> CustomException.from(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
    }

    private StoreEntity findStoreByIdOrThrow(Long storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(() -> CustomException.from(StoreErrorCode.STORE_NOT_FOUND));
    }

    private FeedbackEntity findFeedbackByIdOrThrow(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
            .orElseThrow(() -> CustomException.from(FeedbackErrorCode.FEEDBACK_NOT_FOUND));
    }

    // 수정/삭제하려는 개선사항의 가게 주인이 API 요청한 유저가 맞는지 확인하는 메서드
    private void validateStoreOwner(Long userId, Long storeId) {
        StoreEntity store = findStoreByIdOrThrow(storeId);
        if (!Objects.equals(store.getUserId(), userId)) {
            throw CustomException.from(AnnouncementErrorCode.FORBIDDEN_ANNOUNCEMENT_ACCESS);
        }
    }

    // 참조할 feedback이 해당 가게에 속하는 feedback인지 검증하는 메서드
    private void validateFeedbackBelongsToStore(FeedbackEntity feedback, Long storeId){
        if(!Objects.equals(storeId, feedback.getStoreId())){
            throw CustomException.from(FeedbackErrorCode.FORBIDDEN_FEEDBACK_ACCESS);
        }
    }
}