package com.rekordb.rekordb.tourspot;

import lombok.*;

import javax.persistence.Embeddable;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Address {
    private int areaCode;
    private String addr1;
    private String addr2;
    private String mapx;
    private String mapy;
}
