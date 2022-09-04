package com.rekordb.rekordb.review;

import com.rekordb.rekordb.review.dto.ReviewWriteDTO;
import com.rekordb.rekordb.tourspot.ApiRequest.test.GoogleReview;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.user.domain.userInfo.User;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(updatable = false)
    private LocalDateTime updateAt;

    @ElementCollection
    @CollectionTable(name = "reviewImages",joinColumns = @JoinColumn(name = "idx"))
    private List<String> reviewImages = new ArrayList<>();

    public static Review googleReviewToDB(GoogleReview r, SpotId spotId){
        return Review.builder()
                .fromGoogle(true)
                .spotId(spotId)
                .time(LocalDateTime.ofEpochSecond(r.getTime()*1000,0, ZoneOffset.of("+9")))
                .rating(r.getRating())
                .text(r.getText())
                .userId(UserId.of("Google User"))
                .userName("Google User")
                .build();

    }

    public static Review writeReview(User user, ReviewWriteDTO dto){
        return Review.builder()
                .fromGoogle(false)
                .spotId(SpotId.of(dto.getSpotId()))
                .rating(dto.getRating())
                .text(dto.getText())
                .time(LocalDateTime.now())
                .userId(user.getUserId())
                .userName(user.getNickName())
                .build();
    }

}
