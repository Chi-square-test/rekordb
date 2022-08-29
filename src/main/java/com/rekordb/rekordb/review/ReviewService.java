package com.rekordb.rekordb.review;


import com.rekordb.rekordb.review.Exception.AlreadyExistReviewException;
import com.rekordb.rekordb.review.dto.ReviewDTO;
import com.rekordb.rekordb.review.dto.ReviewWriteDTO;
import com.rekordb.rekordb.review.query.ReviewRepository;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.user.domain.userInfo.User;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import com.rekordb.rekordb.user.query.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewDTO saveReviews(String id, ReviewWriteDTO dto){
        UserId userId = UserId.of(id);
        User user = userRepository.getReferenceById(userId);
        if(reviewRepository.existsByUserIdAndSpotId(userId, SpotId.of(dto.getSpotId()))) throw new AlreadyExistReviewException();
        return ReviewDTO.ConvertToDTO(reviewRepository.save(Review.writeReview(user,dto)));
    }


}
