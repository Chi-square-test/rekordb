package com.rekordb.rekordb.review;


import com.rekordb.rekordb.review.Exception.AlreadyExistReviewException;
import com.rekordb.rekordb.review.dto.ReviewDTO;
import com.rekordb.rekordb.review.dto.ReviewWriteDTO;
import com.rekordb.rekordb.review.query.ReviewRepository;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
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
    private final TourSpotDocumentRepository tourSpotDocumentRepository;

    public ReviewDTO saveReviews(String id, ReviewWriteDTO dto){
        UserId userId = UserId.of(id);
        SpotId spotId = SpotId.of(dto.getSpotId());
        User user = userRepository.getReferenceById(userId);
        if(reviewRepository.existsByUserIdAndSpotId(userId, spotId))throw new AlreadyExistReviewException();
        Review review = reviewRepository.save(Review.writeReview(user,dto));
        return ReviewDTO.ConvertToDTO(review,updateReview(spotId));
    }

    private double updateReview(SpotId spotId){
        TourSpotDocument tourSpotDocument = tourSpotDocumentRepository.findById(spotId).orElseThrow();
        tourSpotDocument.updateRating(reviewRepository.getSpotAverage(spotId));
        return tourSpotDocumentRepository.save(tourSpotDocument).getRating();
    }


}
