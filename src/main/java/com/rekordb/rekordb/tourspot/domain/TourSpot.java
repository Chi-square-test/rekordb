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
    public TourSpot(@NotNull SpotId spotId,  @NotNull String title, @NotNull Address addr, @NotNull SpotCategory cat, List<String> imgs, int typeid, int readCount){
        this.spotId = spotId;
        this.title = title;
        this.address = addr;
        this.typeId = typeid;
        this.readCount = readCount;
        this.spotCategory =cat;
        this.likeCount=0;
        this.rating=0;
        this.images = imgs;
    }

    public void changeImgList(List<String> imgs){
        images.clear();
        images.addAll(imgs);
    }

    public void setGooglePlaceId(String id){
        this.googlePlaceId =id;
    }

    public void updateRating(double rate){ //리뷰가 업데이트될때마다 해당 서비스에서 전체 리뷰수와 평점 수 바탕으로 구하는 로직을 사용해 이 메서드 호출
        this.rating = rate;
    }


    public void plusLikeCount(){
        ++this.likeCount;
    }

    public void minusLikeCount(){
        --this.likeCount;
    }



}
