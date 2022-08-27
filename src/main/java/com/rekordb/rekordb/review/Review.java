package com.rekordb.rekordb.review;

import com.rekordb.rekordb.tourspot.ApiRequest.GoogleReviewDTO;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

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
    private UserId userId;

    @Embedded
    private SpotId spotId;

    private String userName;

    private LocalDateTime time;

    private boolean fromGoogle;

    private int rating;

    private String text;

    @ElementCollection
    @CollectionTable(name = "reviewImages",joinColumns = @JoinColumn(name = "idx"))
    private List<String> reviewImages = new ArrayList<>();

    public static Review googleReviewToDB(GoogleReviewDTO.review dto,SpotId spotId){
        return Review.builder()
                .fromGoogle(true)
                .spotId(spotId)
                .time(LocalDateTime.ofEpochSecond(dto.getTime()*1000,0, ZoneOffset.of("+9")))
                .rating(dto.getRating())
                .text(dto.getText())
                .userId(UserId.of("Google User"))
                .userName("Google User")
                .build();

    }

}
