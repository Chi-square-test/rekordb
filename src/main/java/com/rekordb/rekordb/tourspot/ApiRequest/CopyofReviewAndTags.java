package com.rekordb.rekordb.tourspot.ApiRequest;

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
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "onlyForTags")
public class CopyofReviewAndTags {


    private Long idx;

    private UserId user_id;

    private String spot_id;

    private String userName;

    private LocalDateTime time;

    private boolean fromGoogle;

    private int rating;

    private String text_x;

    private String text_y;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private List<String> tag;



}
