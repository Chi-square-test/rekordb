package com.rekordb.rekordb.domain.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserId implements Serializable {

    @Column(name = "userId")
    private String id;

    public static UserId of(String id){
        return new UserId(id);
    }


}
