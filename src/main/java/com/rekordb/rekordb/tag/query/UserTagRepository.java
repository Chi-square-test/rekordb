package com.rekordb.rekordb.tag.query;

import com.rekordb.rekordb.tag.UserAndTag;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserTagRepository extends MongoRepository<UserAndTag, UserId> {


}
