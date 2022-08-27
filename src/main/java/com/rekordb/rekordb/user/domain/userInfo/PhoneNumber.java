package com.rekordb.rekordb.user.domain.userInfo;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PhoneNumber {
    private String phone;

    public static PhoneNumber of(String num){
        return new PhoneNumber(num);
    }
}
