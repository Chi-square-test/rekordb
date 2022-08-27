package com.rekordb.rekordb.user;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.TagService;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.user.Execption.DuplicateUserInfoException;
import com.rekordb.rekordb.user.domain.userWithSpot.UserWishList;
import com.rekordb.rekordb.user.dto.RekorJoinInformDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class UserWishListController {

    private final UserWishListService userWishListService;

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getWishList(@AuthenticationPrincipal User user){
        ResponseDTO<TourSpotDocument> res = ResponseDTO.<TourSpotDocument>builder()
                .status(ApiStatus.SUCCESS)
                .data(userWishListService.getUserWishList(user.getUsername()))
                .build();
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{spotId}")
    public ResponseEntity<ResponseDTO<?>> addOrDelete(@AuthenticationPrincipal User user, @PathVariable("spotId") String spotId){
        ResponseDTO<Integer> res = ResponseDTO.<Integer>builder()
                .status(ApiStatus.SUCCESS)
                .data(Collections.singletonList(userWishListService.addOrDeleteWishList(user.getUsername(), spotId)))
                .build();
        return ResponseEntity.ok().body(res);
    }


}
