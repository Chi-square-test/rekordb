package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class GoogleReviewDTO {
    ArrayList < Object > html_attributions = new ArrayList < Object > ();
    Result result;
    String status;
    @ToString
    public static class Result{
        review[] reviews;

    }
    @Getter
    @ToString
    public static class review{
        int rating;
        String relative_time_description;
        long time;
        String text;
    }

}
