package com.rekordb.rekordb.tourspot.domain;

import com.rekordb.rekordb.tag.Tag;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of ="spotId")
@Document(collection = "TourSpot")
public class TourSpotDocument {

    @Id
    private SpotId spotId;

    @Indexed
    @TextIndexed
    private String title;

    private String engTitle;

    @Embedded
    private Address address;

    @Embedded
    private EngAddress engAddress;

    @Embedded
    private SpotCategory spotCategory;

    private RekorCategory rekorCategory;

    private List<String> images = new ArrayList<>();

    private int readCount;

    private int typeId;

    private String googlePlaceId;

    @Indexed
    private double rating;

    @Indexed
    private int likeCount;

    private Set<Tag> tagList;


    public TourSpotDocument(TourSpot spot){
        this.spotId = spot.getSpotId();
        this.title = spot.getTitle();
        this.address = spot.getAddress();
        this.tagList = new HashSet<>();
        this.typeId = spot.getTypeId();
        this.readCount =spot.getReadCount();
        this.spotCategory =spot.getSpotCategory();
        this.likeCount=spot.getLikeCount();
        this.rating=spot.getRating();
        this.images =spot.getImages();
        this.rekorCategory = spot.getRekorCategory();
    }

    public void setTagList(Set<Tag> tags){
        this.tagList = tags;
    }

    public void addTagList(Set<Tag> tags){
        tagList.addAll(tags);

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
