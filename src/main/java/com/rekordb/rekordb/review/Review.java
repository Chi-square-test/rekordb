package com.rekordb.rekordb.review;

import com.rekordb.rekordb.tourspot.ApiRequest.GoogleReviewDTO;
import com.rekordb.rekordb.tourspot.SpotId;
import com.rekordb.rekordb.user.UserOauthId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long idx;

    @Embedded
    private UserOauthId userOauthId;

    @Embedded
    private SpotId spotId;

    private String userName;

    private LocalDateTime time;

    private boolean fromGoogle;

    private int rating;

    private String text;

    public static Review googleReviewToDB(GoogleReviewDTO.review dto,SpotId spotId){
        return Review.builder()
                .fromGoogle(true)
                .spotId(spotId)
                .time(LocalDateTime.ofEpochSecond(dto.getTime()*1000,0, ZoneOffset.of("+9")))
                .rating(dto.getRating())
                .text(dto.getText())
                .userOauthId(UserOauthId.of("Google User"))
                .userName("Google User")
                .build();

    }

}
