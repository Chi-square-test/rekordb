package com.rekordb.rekordb.tourspot;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SpotId implements Serializable {

    @Column(name = "spotId")
    private String id;

    public static SpotId of(String id){
        return new SpotId(id);
    }


}
