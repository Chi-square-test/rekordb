package com.rekordb.rekordb.user.domain.userWithSpot;

import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "wishlist")
public class UserWishList {

    @Id
    private UserId userId;

    private Set<TourSpotDocument> wishList;

    public void addWishList(TourSpotDocument document){
        wishList.add(document);
    }

    public void deleteWishList(TourSpotDocument document){
        wishList.remove(document);
    }
}
