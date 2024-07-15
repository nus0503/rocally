package com.company.rocally.domain.travel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTravelIdAndParentIdIsNullOrderByCreatedDateDesc(Long travelId);
}
