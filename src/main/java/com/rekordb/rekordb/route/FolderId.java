package com.rekordb.rekordb.route;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.rekordb.rekordb.tag.TagId;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FolderId {
    private String id;

    public static FolderId newFolderId(){
        return new FolderId(NanoIdUtils.randomNanoId());
    }

    public static FolderId of(String id){
        return new FolderId(id);
    }
}
