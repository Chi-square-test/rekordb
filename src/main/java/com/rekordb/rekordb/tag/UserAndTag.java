package com.rekordb.rekordb.tag;

import com.rekordb.rekordb.user.UserOauthId;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_tag")
public class UserAndTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long idx;

    @Embedded
    @NonNull
    private TagId tagId;

    @Embedded
    @NonNull
    private UserOauthId userOauthId;


}
