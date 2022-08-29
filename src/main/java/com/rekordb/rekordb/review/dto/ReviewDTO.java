package com.rekordb.rekordb.review.dto;

import com.rekordb.rekordb.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    public static ReviewDTO ConvertToDTO(Review review){
        return new ReviewDTO(review.getUserName(),review.getTime(), review.isFromGoogle(), review.getRating(), review.getText());
    }
}
