package com.rekordb.rekordb.tag;

import com.rekordb.rekordb.tourspot.SpotId;
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
