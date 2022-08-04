package com.rekordb.rekordb.domain.Tag;

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
public class TagId implements Serializable {

    private String tagId;

    public static TagId newTagId(){
        return new TagId(NanoIdUtils.randomNanoId());
    }

}
