package com.rekordb.rekordb.user;


import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.user.domain.userInfo.User;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import com.rekordb.rekordb.user.domain.userWithSpot.UserWishList;
import com.rekordb.rekordb.user.query.UserRepository;
import com.rekordb.rekordb.user.query.UserWishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserWishListService {

    private final TourSpotDocumentRepository tourSpotDocumentRepository;
    private final UserRepository userRepository;
    private final UserWishListRepository userWishListRepository;

    @Transactional
    public int addOrDeleteWishList(String userId, String spotId){
        TourSpotDocument spotDocument = tourSpotDocumentRepository.findById(SpotId.of(spotId)).orElseThrow(NoSuchElementException::new);
        UserWishList wishList = userWishListRepository.findById(UserId.of(userId)).orElseThrow(NoSuchElementException::new);
        return wishList.getWishList().contains(spotDocument) ? removeWishList(wishList,spotDocument) : addWishList(wishList,spotDocument);
    }


    private int addWishList(UserWishList wishList,TourSpotDocument spotDocument){
        wishList.addWishList(spotDocument);
        spotDocument.plusLikeCount();
        userWishListRepository.save(wishList);
        return tourSpotDocumentRepository.save(spotDocument).getLikeCount();
    }


    private int removeWishList(UserWishList wishList,TourSpotDocument spotDocument){
        wishList.deleteWishList(spotDocument);
        spotDocument.minusLikeCount();
        userWishListRepository.save(wishList);
        return tourSpotDocumentRepository.save(spotDocument).getLikeCount();
    }

    public List<TourSpotDocument> getUserWishList(String userId){
        UserWishList wishList = userWishListRepository.findById(UserId.of(userId)).orElseThrow(NoSuchElementException::new);
        return new ArrayList<>(wishList.getWishList());
    }

}
