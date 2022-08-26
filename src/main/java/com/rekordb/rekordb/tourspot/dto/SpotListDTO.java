package com.rekordb.rekordb.tourspot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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




}
