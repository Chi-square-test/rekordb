package com.rekordb.rekordb.tourspot.ApiRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

@Deprecated
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
        @JsonProperty("author_name")
        private String authorName;
        @JsonProperty("author_url")
        private String authorUrl;
        @JsonProperty("language")
        private String language;
        @JsonProperty("profile_photo_url")
        private String profilePhotoUrl;
        @JsonProperty("rating")
        private Integer rating;
        @JsonProperty("relative_time_description")
        private String relativeTimeDescription;
        @JsonProperty("text")
        private String text;
        @JsonProperty("time")
        private Integer time;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    }

}
