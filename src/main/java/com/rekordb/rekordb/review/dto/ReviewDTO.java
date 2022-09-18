package com.rekordb.rekordb.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rekordb.rekordb.review.Review;
import com.rekordb.rekordb.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private String userName;
    private LocalDateTime time;
    private boolean fromGoogle;
    private int rating;
    private String text;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double currentRates;
    private List<Tag> tagList;

    public static ReviewDTO ConvertToDTO(Review review,List<Tag> tagList, double currentRates){
        return new ReviewDTO(review.getUserName(),review.getTime(), review.isFromGoogle(), review.getRating(), review.getText(),currentRates,tagList);
    }
    public static ReviewDTO ConvertToDTO(Review review,List<Tag> tagList){
        return new ReviewDTO(review.getUserName(),review.getTime(), review.isFromGoogle(), review.getRating(), review.getText(),null,tagList);
    }
}
