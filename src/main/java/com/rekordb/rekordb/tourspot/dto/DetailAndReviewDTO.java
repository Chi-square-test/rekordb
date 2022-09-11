package com.rekordb.rekordb.tourspot.dto;

import com.rekordb.rekordb.review.Review;
import com.rekordb.rekordb.review.dto.ReviewDTO;
import com.rekordb.rekordb.tourspot.domain.Address;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.CommonItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.DetailItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.ImageItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.TourSpotDetail;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
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
    private SpotListDTO spotInfo;
    private CommonItem commonInfo; //detailCommon
    private List<DetailItem> detailList; //detailInfo
    private Map<String,String> plusInfo; //detailIntro
    private List<ImageItem> imageList;  //detailImage
    private List<ReviewDTO> reviewList;
    private CheckItem checkItem;

    public static DetailAndReviewDTO convertToDTO(TourSpotDocument document, TourSpotDetail detail, List<Review> review, CheckItem checkItem){
        return DetailAndReviewDTO.builder()
                .spotInfo(new SpotListDTO(document))
                .spotId(detail.getSpotId())
                .commonInfo(detail.getCommonItem())
                .detailList(detail.getDetailItems())
                .plusInfo(detail.getDetailIntro())
                .imageList(detail.getImageItems())
                .reviewList(review.stream().map(ReviewDTO::ConvertToDTO).collect(Collectors.toList()))
                .checkItem(checkItem)
                .build();
    }
}
