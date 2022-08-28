package com.rekordb.rekordb.user.query;

import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import com.rekordb.rekordb.user.domain.userWithSpot.UserWishList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface UserWishListRepository extends MongoRepository<UserWishList, UserId> {

    int countByWishListIn(Set<TourSpotDocument> documents);


}
