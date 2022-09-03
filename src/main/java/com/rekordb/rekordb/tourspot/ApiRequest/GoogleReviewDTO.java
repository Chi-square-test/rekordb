package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class GoogleReviewDTO implements Serializable {
    ArrayList < Object > html_attributions = new ArrayList<> ();
    Result result;
    String status;
    @ToString
    public static class Result implements Serializable{
        ArrayList<review> reviews;

    }
    @Getter
    @ToString
    public static class review implements Serializable{
        int rating;
        String relative_time_description;
        long time;
        String text;
    }

}
