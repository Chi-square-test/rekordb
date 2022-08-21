package com.rekordb.rekordb.tourspot;

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
public class SpotId implements Serializable {

    @Column(name = "spot_Id")
    private String id;

    public static SpotId of(String id){
        return new SpotId(id);
    }

    public static SpotId createSpotId(){return new SpotId(NanoIdUtils.randomNanoId());}


}
