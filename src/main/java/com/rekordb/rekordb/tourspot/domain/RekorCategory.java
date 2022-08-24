package com.rekordb.rekordb.tourspot.domain;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum RekorCategory {
    LANDSCAPE("K-LANDSCAPE",0),
    CULTURE("K-CULTURE",1),
    FOOD("K-FOOD",2),
    SHOPPING("K-SHOPPING",3),
    POP("K-POP",4),
    DRAMA("K-DRAMA",5),
    FESTIVAL("K-FESTIVAL",6),
    LEISURE("K-LEISURE",7),
    OTHER("OTHER",8);

    private String KName;
    private int idx;

    @Override
    public String toString(){
        return KName;
    }

    public int idx(){
        return idx;
    }

    private static final Map<Integer,RekorCategory> BY_INDEX=
            Stream.of(values()).collect(Collectors.toMap(RekorCategory::idx, Function.identity()));

    public static RekorCategory valueOfIndex(int index){
        return BY_INDEX.get(index);
    }


}
