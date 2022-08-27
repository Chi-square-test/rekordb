package com.rekordb.rekordb.tag;

import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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
