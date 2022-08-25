package com.rekordb.rekordb.tourspot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rekordb.rekordb.tourspot.domain.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"spotId"})
public class SpotByCatDTO {
    private SpotId spotId;

    private String title;

    private Address address;

    @Convert(converter = RekorCategoryAttributeConverter.class)
    private RekorCategory rekorCategory;


    @ElementCollection
    @CollectionTable(name = "images",joinColumns = @JoinColumn(name = "spotId"))
    private List<String> images = new ArrayList<>();

    private double rating;

    private int likeCount;


}
