package com.company.rocally.domain.travel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTravelIdAndParentIdIsNullOrderByCreatedDateDesc(Long travelId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.travel.id = :travelId")
    int countByTravelId(@Param("travelId") Long travelId);

}
