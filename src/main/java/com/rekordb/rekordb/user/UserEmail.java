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
public class UserEmail implements Serializable {

    @Column(name = "email")
    private String email;

    public static UserEmail of(String id){
        return new UserEmail(id);
    }


}
