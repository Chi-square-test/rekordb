package com.rekordb.rekordb.review.query;

import com.rekordb.rekordb.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
