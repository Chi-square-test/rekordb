package com.rekordb.rekordb.tourspot.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@AllArgsConstructor
public enum SortBy {
    title("Name") {
        @Override
        public Sort getSort() {
            return Sort.by(Sort.Direction.ASC, this.name());
        }
    } ,
    likeCount("Likes"){
        @Override
        public Sort getSort() {
            return Sort.by(Sort.Direction.DESC, this.name());
        }
    },
    rating("Rates"){
        @Override
        public Sort getSort() {
            return Sort.by(Sort.Direction.DESC, this.name());
        }
    };


    private String front;

    public abstract Sort getSort();

    public static SortBy getByFront(String name){
        return Arrays.stream(values())
                .filter(value -> value.front.equals(name))
                .findAny()
                .orElse(null);
    }
}
