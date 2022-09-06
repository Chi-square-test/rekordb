package com.rekordb.rekordb.course.dto;

import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseSpotDTO {
    private String spotId;
    private boolean isWished;
    private boolean isRecommend;

    public SpotWithCheck convertToValue(TourSpotDocument document){
        return new SpotWithCheck(document,isWished,isRecommend);
    }

}
