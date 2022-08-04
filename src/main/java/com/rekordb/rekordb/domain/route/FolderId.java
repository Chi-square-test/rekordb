package com.rekordb.rekordb.domain.route;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.rekordb.rekordb.domain.Tag.TagId;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FolderId {
    private String id;

    public static TagId newFolderId(){
        return new TagId(NanoIdUtils.randomNanoId());
    }
}
