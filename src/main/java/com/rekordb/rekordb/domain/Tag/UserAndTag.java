package com.rekordb.rekordb.domain.Tag;

import com.rekordb.rekordb.domain.user.UserId;
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
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    @NonNull
    private TagId tagId;

    @Embedded
    @NonNull
    private UserId userId;


}
