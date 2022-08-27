package com.rekordb.rekordb.user.domain.userInfo;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Password {

    private String encPassword;

    public static Password encryptPassword(PasswordEncoder encoder, String pw){
        return new Password(encoder.encode(pw));
    }
}
