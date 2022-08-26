package com.rekordb.rekordb.tag;

import com.rekordb.rekordb.user.domain.UserId;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "user_tag")
public class UserAndTag {

    @Id
    private UserId userId;

    @NonNull
    private List<Tag> tagList;

}
