package com.rekordb.rekordb.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean isWished;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean isRecommend;

    public SpotWithCheck convertToValue(TourSpotDocument document){
        return new SpotWithCheck(document,isWished,isRecommend);
    }

}
