package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class GoogleReviewDTO {
    int rating;
    String relative_time_description;
    long time;
    String text;

}
