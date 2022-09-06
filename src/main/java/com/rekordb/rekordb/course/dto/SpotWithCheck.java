package com.rekordb.rekordb.course.dto;

import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class SpotWithCheck  {
    private TourSpotDocument document;
    private boolean isWished;
    private boolean isRecommend;


}
