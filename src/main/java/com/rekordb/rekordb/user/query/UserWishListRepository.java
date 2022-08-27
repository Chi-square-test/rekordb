package com.rekordb.rekordb.user.query;

import com.rekordb.rekordb.user.domain.userInfo.UserId;
import com.rekordb.rekordb.user.domain.userWithSpot.UserWishList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserWishListRepository extends MongoRepository<UserWishList, UserId> {


}
