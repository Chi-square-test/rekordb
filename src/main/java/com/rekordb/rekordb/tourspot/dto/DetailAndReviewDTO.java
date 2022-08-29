package com.rekordb.rekordb.tourspot.dto;

import com.rekordb.rekordb.review.Review;
import com.rekordb.rekordb.review.dto.ReviewDTO;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.CommonItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.DetailItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.ImageItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.TourSpotDetail;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DetailAndReviewDTO {
    private SpotId spotId;
    private CommonItem commonInfo; //detailCommon
    private List<DetailItem> detailList; //detailInfo
    private Map<String,String> plusInfo; //detailIntro
    private List<ImageItem> imageList;  //detailImage
    private List<ReviewDTO> reviewList;
    private boolean isReviewed;

    public static DetailAndReviewDTO ConvertToDTO(TourSpotDetail detail, List<Review> review, boolean isReviewed){
        return DetailAndReviewDTO.builder()
                .spotId(detail.getSpotId())
                .commonInfo(detail.getCommonItem())
                .detailList(detail.getDetailItems())
                .plusInfo(detail.getDetailIntro())
                .imageList(detail.getImageItems())
                .reviewList(review.stream().map(ReviewDTO::ConvertToDTO).collect(Collectors.toList()))
                .isReviewed(isReviewed)
                .build();
    }
}
