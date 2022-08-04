package com.rekordb.rekordb.tag;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TagId implements Serializable {

    private String id;

    public static TagId newTagId(){
        return new TagId(NanoIdUtils.randomNanoId());
    }

}
