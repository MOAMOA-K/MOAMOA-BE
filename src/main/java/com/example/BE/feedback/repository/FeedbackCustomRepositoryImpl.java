package com.example.BE.feedback.repository;

import com.example.BE.feedback.controller.dto.FeedbackOwnerResponse;
import com.example.BE.feedback.controller.dto.FeedbackSearchRequest;
import com.example.BE.feedback.domain.QFeedbackEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedbackCustomRepositoryImpl implements FeedbackCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FeedbackOwnerResponse> search(FeedbackSearchRequest request) {
        QFeedbackEntity f = QFeedbackEntity.feedbackEntity;

        BooleanBuilder where = buildWhere(request, f);

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
                .where(where)
                .orderBy(f.createdAt.desc())
                .offset(request.pageable().getOffset())
                .limit(request.pageable().getPageSize())
                .fetch();

        Long total = queryFactory
                .select(f.count())
                .from(f)
                .where(where)
                .fetchOne();

        return new PageImpl<>(content, request.pageable(), total == null ? 0 : total);
    }

    private BooleanBuilder buildWhere(FeedbackSearchRequest req, QFeedbackEntity f) {
        BooleanBuilder where = new BooleanBuilder();
        if (req.userId() != null) {
            where.and(f.userId.eq(req.userId()));
        }
        if (req.storeId() != null) {
            where.and(f.storeId.eq(req.storeId()));
        }
        if (req.type() != null) {
            where.and(f.type.eq(req.type()));
        }
        if (req.status() != null) {
            where.and(f.status.eq(req.status()));
        }
        return where;
    }
}

