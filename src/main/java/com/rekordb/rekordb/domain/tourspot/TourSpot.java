package com.rekordb.rekordb.domain.tourspot;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
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

    @Embedded
    private Address address;

    @Embedded
    private Category category;


    @ElementCollection
    @CollectionTable(name = "imgs",joinColumns = @JoinColumn(name = "spotId"))
    private List<String> images = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "tags",joinColumns = @JoinColumn(name = "spotId"))
    private List<String> tags = new ArrayList<>();

    private int readCount;

    private int typeId;

    private String googlePlaceId;

    private int likeCount;

    @Builder
    public TourSpot(SpotId spotId, String title, Address addr, Category cat, int typeid, int readCount){
        Assert.notNull(spotId,"장소 id가 존재하지 않습니다.");
        Assert.notNull(addr,"장소 주소가 존재하지 않습니다. ");
        Assert.hasText(title,"장소 이름이 존재하지 않습니다.");
        Assert.notNull(cat,"장소 카테고리가 존재하지 않습니다. ");
        this.spotId = spotId;
        this.title = title;
        this.address = addr;
        this.typeId = typeid;
        this.readCount = readCount;
        this.category =cat;
        this.likeCount=0;
    }

    public void changeImgList(List<String> imgs){
        images.clear();
        images.addAll(imgs);
    }


    public void plusLikeCount(){
        ++this.likeCount;
    }

    public void minusLikeCount(){
        --this.likeCount;
    }



}
