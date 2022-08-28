package com.rekordb.rekordb.user;

import com.rekordb.rekordb.security.query.RefreshTokenRepository;
import com.rekordb.rekordb.tag.query.UserTagRepository;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.user.domain.userInfo.User;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import com.rekordb.rekordb.user.query.UserRepository;
import com.rekordb.rekordb.user.query.UserWishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final UserWishListRepository userWishListRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TourSpotDocumentRepository tourSpotDocumentRepository;

    @Transactional
    public void removeUser(String id){
        UserId userId = UserId.of(id);
        User user = userRepository.getReferenceById(userId);
        refreshTokenRepository.delete(refreshTokenRepository.getReferenceById(userId));
        Set<TourSpotDocument> documents = userWishListRepository.findById(userId).orElseThrow().getWishList();
        userWishListRepository.delete(userWishListRepository.findById(userId).orElseThrow());
        userTagRepository.delete(userTagRepository.findById(userId).orElseThrow());
        userRepository.delete(user);
        updateWishCount(documents);


    }

    private void updateWishCount(Set<TourSpotDocument> documents){
        documents.forEach(document -> document.setLikeCount(userWishListRepository.countByWishListIn(Collections.singleton(document))));
        tourSpotDocumentRepository.saveAll(documents);
    }



}
