package com.company.rocally.domain.travel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TravelRepository extends JpaRepository<Travel, Long> {

    // Query어노테이션 안에 쿼리를 작성할 때, 엔티티 클래스의 필드명을 작성해야 하는데 데이터베이스 컬럼 명을 작성해서 에러가 남 * t.like_count X  t.likeCount O
    @Modifying
    @Query(value = "UPDATE Travel t SET t.likeCount = t.likeCount + 1 WHERE t.id = :id")
    void increaseLikeCount(Long id);

    @Modifying
    @Query(value = "UPDATE Travel t SET t.likeCount = t.likeCount - 1 WHERE t.id = :id")
    void decreaseLikeCount(Long id);

    @Modifying
    @Query(value = "UPDATE Travel t SET t.viewCount = t.viewCount + 1 WHERE t.id = :id")
    void updateView(@Param("id") Long id);

    Page<Travel> findByTitleContaining(String q, Pageable pageable);

//    //    @Transactional
//    @Modifying
//    @Query("UPDATE Travel t SET t.likeCount = t.likeCount + 1 WHERE t.id = :travelId")
//    void increaseLikeCount(Long travelId);
//
//    //    @Transactional
//    @Modifying
//    @Query("UPDATE Travel t SET t.likeCount = t.likeCount - 1 WHERE t.id = :travelId")
//    void decreaseLikeCount(Long travelId);
}
