package com.example.BE.announcement.controller;

import com.example.BE.announcement.domain.dto.AnnouncementCreateRequest;
import com.example.BE.announcement.domain.dto.AnnouncementResponse;
import com.example.BE.announcement.domain.dto.AnnouncementUpdateRequest;
import com.example.BE.announcement.service.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementApiController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<AnnouncementResponse> createAnnouncement(
        @AuthenticationPrincipal Long userId,
        @Valid @RequestBody AnnouncementCreateRequest request
    ) {
        AnnouncementResponse response = announcementService.createAnnouncement(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> getAnnouncement(
        @PathVariable Long announcementId
    ) {
        AnnouncementResponse response = announcementService.getAnnouncementById(announcementId);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/list/{storeId}")
//    public ResponseEntity<List<AnnouncementResponse>> getAnnouncementList(
//        @PathVariable Long storeId
//    ) {
//        List<AnnouncementResponse> response = announcementService.getAnnouncementsByStoreId(storeId);
//        return ResponseEntity.ok(response);
//    }

    @PatchMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> updateAnnouncement(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long announcementId,
        @Valid @RequestBody AnnouncementUpdateRequest request
    ) {
        AnnouncementResponse response = announcementService.updateAnnouncement(userId, announcementId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{announcementId}")
    public ResponseEntity<Void> deleteAnnouncement(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long announcementId
    ) {
        announcementService.deleteAnnouncement(userId, announcementId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}