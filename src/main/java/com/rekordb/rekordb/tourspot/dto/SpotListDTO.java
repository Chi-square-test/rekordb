package com.rekordb.rekordb.tourspot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rekordb.rekordb.course.dto.SpotWithCheck;
import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tourspot.domain.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"spotId"})
public class SpotListDTO {
    private SpotId spotId;

    private String title;

    private Address address;

    private RekorCategory rekorCategory;

    private List<String> images;

    private double rating;

    private int likeCount;

    private Set<Tag> tags;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isWished;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isRecommend;

    public SpotListDTO(TourSpotDocument document){
        this.spotId = document.getSpotId();
        this.title = document.getTitle();
        this.address = document.getAddress();
        this.rekorCategory = document.getRekorCategory();
        this.images = document.getImages();
        this.rating = document.getRating();
        this.likeCount = document.getLikeCount();
        this.tags = document.getTagList();
    }

    public SpotListDTO(SpotWithCheck spot){
        this.spotId = spot.getDocument().getSpotId();
        this.title =  spot.getDocument().getTitle();
        this.address =  spot.getDocument().getAddress();
        this.rekorCategory =  spot.getDocument().getRekorCategory();
        this.images =  spot.getDocument().getImages();
        this.rating =  spot.getDocument().getRating();
        this.likeCount =  spot.getDocument().getLikeCount();
        this.tags =  spot.getDocument().getTagList();
        this.isWished = spot.isWished();
        this.isRecommend = spot.isRecommend();
    }




}
