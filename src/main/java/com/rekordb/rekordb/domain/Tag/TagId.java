package com.rekordb.rekordb.domain.Tag;

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

    private Long tagId;

    public static TagId of(Long tagId){
        return new TagId((tagId));
    }

}
