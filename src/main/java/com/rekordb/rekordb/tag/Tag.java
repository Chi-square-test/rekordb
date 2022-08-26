package com.rekordb.rekordb.tag;

import com.rekordb.rekordb.tourspot.domain.RekorCategory;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "tag")
@EqualsAndHashCode(of = {"tagName"})
public class Tag {

    @Id
    private TagId tagId;

    @Indexed(unique = true)
    private String tagName;

    private String engTagName;

    private String tagCategory;

    private RekorCategory defaultCategory;



    public static Tag makeNewTag(String name){
        return new Tag(TagId.newTagId(),name,"","",null);
    }

    public static Tag makeNewTag(String name,RekorCategory category){
        return new Tag(TagId.newTagId(),name,"","",category);
    }

}
