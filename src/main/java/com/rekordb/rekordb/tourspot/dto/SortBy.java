package com.rekordb.rekordb.tourspot.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public enum SortBy {
    title("Name") ,
    likcCount("Likes"),
    rating("rates");


    private String front;

    public Sort getSort() {
        return Sort.by(Sort.Direction.ASC, this.name());
    }
}
