package com.rekordb.rekordb.review;


import com.rekordb.rekordb.review.Exception.AlreadyExistReviewException;
import com.rekordb.rekordb.review.dto.ReviewDTO;
import com.rekordb.rekordb.review.dto.ReviewWriteDTO;
import com.rekordb.rekordb.review.query.ReviewRepository;
import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.TagId;
import com.rekordb.rekordb.tag.query.TagRepository;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.user.domain.userInfo.User;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import com.rekordb.rekordb.user.query.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TourSpotDocumentRepository tourSpotDocumentRepository;
    private final TagRepository tagRepository;

    public ReviewDTO saveReviews(String id, ReviewWriteDTO dto){
        UserId userId = UserId.of(id);
        SpotId spotId = SpotId.of(dto.getSpotId());
        User user = userRepository.getReferenceById(userId);
        if(reviewRepository.existsByUserIdAndSpotId(userId, spotId))throw new AlreadyExistReviewException();
        Review review = reviewRepository.save(Review.writeReview(user,dto));
        return ReviewDTO.ConvertToDTO(review,getReviewTagList(review),updateReview(spotId));
    }

    private double updateReview(SpotId spotId){
        TourSpotDocument tourSpotDocument = tourSpotDocumentRepository.findById(spotId).orElseThrow();
        tourSpotDocument.updateRating(reviewRepository.getSpotAverage(spotId));
        return tourSpotDocumentRepository.save(tourSpotDocument).getRating();
    }

    public List<Tag> getReviewTagList(Review review){
        return review.getReviewTagList().stream()
                .map(TagId::of)
                .map(tagRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }


}
