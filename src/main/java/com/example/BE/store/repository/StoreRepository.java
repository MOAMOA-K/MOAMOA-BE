package com.example.BE.store.repository;

import com.example.BE.store.domain.StoreEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    // 키워드 검색 (공백 없는 이름으로 검색)
    Optional<StoreEntity> findByCanonicalNameContaining(String canonicalName);

    // 이거는 근데 AI 도움을 받은 거라 테스트 좀 해 봐야 해요.
    // 지도 검색을 위한 공간 쿼리 (MySQL 기준)
    @Query(value = "SELECT * FROM store s " +
        "WHERE ST_Distance_Sphere(POINT(s.longitude, s.latitude), POINT(:lon, :lat)) <= :radius " +
        "AND (s.name LIKE CONCAT('%', :keyword, '%') OR s.canonical_name LIKE CONCAT('%', "
        + ":keyword, '%'))",
        nativeQuery = true)
    List<StoreEntity> findNearbyStoresWithKeyword(
        @Param("lat") Double lat,
        @Param("lon") Double lon,
        @Param("radius") int radius,
        @Param("keyword") String keyword
    );
}
