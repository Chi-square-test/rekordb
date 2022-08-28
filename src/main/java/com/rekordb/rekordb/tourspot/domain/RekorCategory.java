package com.rekordb.rekordb.tourspot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum RekorCategory {
    LANDSCAPE("K-LANDSCAPE",0, Arrays.asList("웅장함", "자연", "힐링", "뷰", "풍경")),
    CULTURE("K-CULTURE",1, Arrays.asList("전통", "히스토리")),
    FOOD("K-FOOD",2, Arrays.asList("제철 음식", "전통", "JMT")),
    SHOPPING("K-SHOPPING",3, Arrays.asList("패션","KRW")),
    POP("K-POP",4, Arrays.asList("BTS", "뮤직", "댄스")),
    DRAMA("K-DRAMA",5, Arrays.asList("촬영지", "세트장", "흥미로움")),
    FESTIVAL("K-FESTIVAL",6, Arrays.asList("황홀함", "music")),
    LEISURE("K-LEISURE",7, Arrays.asList("남녀노소", "playing")),
    OTHER("OTHER",8, Collections.emptyList());

    private String KName;
    private int idx;
    private List<String> defaultTagNameList;

    @JsonValue
    @Override
    public String toString(){
        return KName;
    }

    public int idx(){
        return idx;
    }

    public List<String> getDefaultTagNameList(){return defaultTagNameList;}

    private static final Map<Integer,RekorCategory> BY_INDEX=
            Stream.of(values()).collect(Collectors.toMap(RekorCategory::idx, Function.identity()));

    public static RekorCategory valueOfIndex(int index){
        return BY_INDEX.get(index);
    }


}
