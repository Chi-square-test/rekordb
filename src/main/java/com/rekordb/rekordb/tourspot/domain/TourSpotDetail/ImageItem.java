package com.rekordb.rekordb.tourspot.domain.TourSpotDetail;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ImageItem {
    private String originimgurl;
    private String smallimageurl;
}
