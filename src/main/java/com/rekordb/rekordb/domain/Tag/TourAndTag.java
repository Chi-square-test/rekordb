package com.rekordb.rekordb.domain.Tag;

import com.rekordb.rekordb.domain.tourspot.SpotId;
import com.rekordb.rekordb.domain.tourspot.TourSpot;
import com.rekordb.rekordb.domain.user.UserId;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tour_tag")
public class TourAndTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    @NonNull
    private TagId tagId;

    @Embedded
    @NonNull
    private SpotId spotId;

}
