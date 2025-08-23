package com.example.BE.feedback.repository;

import com.example.BE.feedback.controller.dto.FeedbackOwnerResponse;
import com.example.BE.feedback.controller.dto.FeedbackSearchRequest;
import com.example.BE.feedback.domain.FeedbackStatus;
import com.example.BE.feedback.domain.FeedbackType;
import com.example.BE.feedback.domain.QFeedbackEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedbackCustomRepositoryImpl implements FeedbackCustomRepository {

    private final JPAQueryFactory queryFactory;
    private static final QFeedbackEntity f = QFeedbackEntity.feedbackEntity;

    @Override
    public Page<FeedbackOwnerResponse> search(FeedbackSearchRequest request) {

        BooleanExpression conditions = buildConditions(request);

        List<FeedbackOwnerResponse> content = queryFactory
                .select(Projections.constructor(FeedbackOwnerResponse.class,
                        f.id,
                        f.rating,
                        f.modifiedContent,
                        f.reply,
                        f.type,
                        f.status,
                        f.createdAt
                ))
                .from(f)
                .where(conditions)
                .orderBy(f.id.desc())
                .offset(request.pageable().getOffset())
                .limit(request.pageable().getPageSize())
                .fetch();

        Long total = queryFactory
                .select(f.count())
                .from(f)
                .where(conditions)
                .fetchOne();

        return new PageImpl<>(content, request.pageable(), Objects.requireNonNullElse(total, 0L));
    }

    private BooleanExpression buildConditions(FeedbackSearchRequest req) {
        return Stream.of(
                        userIdEq(req.userId()),
                        storeIdEq(req.storeId()),
                        typeEq(req.type()),
                        statusEq(req.status())
                )
                .filter(Objects::nonNull)
                .reduce(BooleanExpression::and)
                .orElse(null);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? f.userId.eq(userId) : null;
    }

    private BooleanExpression storeIdEq(Long storeId) {
        return storeId != null ? f.storeId.eq(storeId) : null;
    }

    private BooleanExpression typeEq(FeedbackType type) {
        return type != null ? f.type.eq(type) : null;
    }

    private BooleanExpression statusEq(FeedbackStatus status) {
        return status != null ? f.status.eq(status) : null;
    }
}

