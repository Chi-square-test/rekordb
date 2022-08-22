package com.rekordb.rekordb.tourspot.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class EngAddress {
    private String EngAddr1;
    private String EngAddr2;
}
