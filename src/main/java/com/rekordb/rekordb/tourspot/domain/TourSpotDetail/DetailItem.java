package com.rekordb.rekordb.tourspot.domain.TourSpotDetail;


import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class DetailItem {
    private String infoname;
    private String infotext;
}
