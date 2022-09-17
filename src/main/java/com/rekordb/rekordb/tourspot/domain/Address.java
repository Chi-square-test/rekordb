package com.rekordb.rekordb.tourspot.domain;

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

    public static Address convertToEng(Address address ,EngAddress engAddress){
        return Address.builder()
                .addr1(engAddress.getEngAddr1())
                .addr2(engAddress.getEngAddr2())
                .areaCode(address.getAreaCode())
                .mapx(address.getMapx())
                .mapy(address.getMapy())
                .build();
    }
}
