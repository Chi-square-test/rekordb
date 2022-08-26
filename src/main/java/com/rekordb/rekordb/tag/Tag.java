package com.rekordb.rekordb.tag;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "tag")
public class Tag {

    @Id
    private TagId tagId;

    @NonNull
    private String tagName;

    @NonNull
    private String EngTagName;

    private String tagCategory;

}
