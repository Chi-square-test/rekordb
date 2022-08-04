package com.rekordb.rekordb.domain.Tag;

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

    private String tagCategory;

}
