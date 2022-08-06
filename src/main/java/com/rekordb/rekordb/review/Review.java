package com.rekordb.rekordb.review;

import com.rekordb.rekordb.tourspot.SpotId;
import com.rekordb.rekordb.user.UserEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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
    private UserEmail userEmail;

    @Embedded
    private SpotId spotId;

    private String userName;

    private LocalDateTime time;

    private boolean fromGoogle;

    private int rating;

    private String text;

}
