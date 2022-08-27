package com.rekordb.rekordb.user.domain.userInfo;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
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

    @Column(name = "UserId")
    private String userId;

    public static UserId of(String id){
        return new UserId(id);
    }
    public static UserId createUserId(){return new UserId(NanoIdUtils.randomNanoId());}

    @Override
    public String toString(){
        return userId;
    }


}
