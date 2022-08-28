package com.rekordb.rekordb.user;

import com.rekordb.rekordb.security.query.RefreshTokenRepository;
import com.rekordb.rekordb.tag.query.UserTagRepository;
import com.rekordb.rekordb.tourspot.domain.SpotId;
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

import javax.swing.text.Document;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        log.info("삭제 유저 : "+user.getNickName());
        refreshTokenRepository.delete(refreshTokenRepository.getReferenceById(userId));
        userWishListRepository.findById(userId).ifPresent(userWishList -> updateWishCount(userWishList.getWishList()));
        userWishListRepository.findById(userId).ifPresent(userWishListRepository::delete);
        userTagRepository.findById(userId).ifPresent(userTagRepository::delete);
        userRepository.delete(user);
    }

    private void updateWishCount(Set<TourSpotDocument> documents){
        log.info("재조정 대상 "+documents.stream().map(TourSpotDocument::getTitle).collect(Collectors.joining()) );
        documents.forEach(document -> document.setLikeCount(userWishListRepository.countByWishListIn(Collections.singleton(document))));
        tourSpotDocumentRepository.saveAll(documents);
    }

    public void updateWishCount(String spotId){
        SpotId id = SpotId.of(spotId);
        tourSpotDocumentRepository.findById(id).ifPresent(document -> updateWishCount(Collections.singleton(document)));
    }



}
