package com.rekordb.rekordb.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserOauthId implements Serializable {

    @Column(name = "OauthId")
    private String oauthId;

    public static UserOauthId of(String id){
        return new UserOauthId(id);
    }


}
