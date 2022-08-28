package com.rekordb.rekordb.tourspot.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@AllArgsConstructor
public enum SortBy {
    title("Name") ,
    likcCount("Likes"),
    rating("Rates");


    private String front;

    public Sort getSort() {
        return Sort.by(Sort.Direction.ASC, this.name());
    }

    public static SortBy getByFront(String name){
        return Arrays.stream(values())
                .filter(value -> value.front.equals(name))
                .findAny()
                .orElse(null);
    }
}
