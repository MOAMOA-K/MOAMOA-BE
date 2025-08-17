package com.example.BE.user.repository;

import com.example.BE.user.domain.UserEntity;
import com.example.BE.user.domain.dto.UserDetailResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
                select new com.example.BE.user.domain.dto.UserDetailResponse(
                    u.id,
                    u.nickname,
                    u.email,
                    u.points,
                    (select (count(f))      from FeedbackEntity f     where f.userId = u.id),
                    (select (count(uc))      from UserCouponEntity uc       where uc.userId = u.id and uc.isUsed = false)
                )
                from UserEntity u
                where u.id = :userId
            """)
    UserDetailResponse findMyInfo(@Param("userId") Long userId);

}