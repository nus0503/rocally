package com.company.rocally.domain.heart;

import com.company.rocally.domain.travel.Travel;
import com.company.rocally.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserAndTravel(User user, Travel travel);

    boolean existsByUserIdAndTravelId(Long userId, Long travelId);
}
