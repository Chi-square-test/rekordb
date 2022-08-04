package com.rekordb.rekordb.tag;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tag")
public class Tag {

    @EmbeddedId
    private TagId tagId;

    @NonNull
    private String tagName;

    @NonNull
    private String EngTagName;

    private String tagCategory;

}