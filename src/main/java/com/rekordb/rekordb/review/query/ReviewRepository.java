package com.rekordb.rekordb.review.query;

import com.rekordb.rekordb.review.Review;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findBySpotId(SpotId spotId);
    boolean existsByUserIdAndSpotId(UserId userId,SpotId spotId);

}
