package com.rekordb.rekordb.tourspot.domain;

import com.rekordb.rekordb.tourspot.domain.Address;
import com.rekordb.rekordb.tourspot.domain.EngAddress;
import com.rekordb.rekordb.tourspot.domain.SpotCategory;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Deprecated(since = "Use instead TourSpotDocument")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tourSpot")
public class TourSpot {

    @EmbeddedId
    private SpotId spotId;

    private String title;

    private String engTitle;

    @Embedded
    private Address address;

    @Embedded
    private EngAddress engAddress;

    @Embedded
    private SpotCategory spotCategory;

    @Convert(converter = RekorCategoryAttributeConverter.class)
    private RekorCategory rekorCategory;


    @ElementCollection
    @CollectionTable(name = "images",joinColumns = @JoinColumn(name = "spotId"))
    private List<String> images = new ArrayList<>();

    private int readCount;

    private int typeId;

    private String googlePlaceId;

    private double rating;

    private int likeCount;

    @Builder
    public TourSpot(@NotNull SpotId spotId,  @NotNull String title, @NotNull Address addr, @NotNull SpotCategory cat, List<String> imgs, int typeid, int readCount, RekorCategory rekorCategory){
        this.spotId = spotId;
        this.title = title;
        this.address = addr;
        this.typeId = typeid;
        this.readCount = readCount;
        this.spotCategory =cat;
        this.likeCount=0;
        this.rating=0;
        this.images = imgs;
        this.rekorCategory = rekorCategory;
    }

    public void changeImgList(List<String> imgs){
        images.clear();
        images.addAll(imgs);
    }
    public void deleteGooglePlaceId(){this.googlePlaceId=null;}

    public void setGooglePlaceId(String id){
        this.googlePlaceId =id;
    }

    public void updateRating(double rate){ //????????? ???????????????????????? ?????? ??????????????? ?????? ???????????? ?????? ??? ???????????? ????????? ????????? ????????? ??? ????????? ??????
        this.rating = rate;
    }


    public void plusLikeCount(){
        ++this.likeCount;
    }

    public void minusLikeCount(){
        --this.likeCount;
    }



}
