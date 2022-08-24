package com.rekordb.rekordb.tourspot.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RekorCategory {
    LANDSCAPE("K-LANDSCAPE"),
    CULTURE("K-CULTURE"),
    FOOD("K-FOOD"),
    SHOPPING("K-SHOPPING"),
    POP("K-POP"),
    DRAMA("K-DRAMA"),
    FESTIVAL("K-FESTIVAL"),
    LEISURE("K-LEISURE");

    private String KName;

    @Override
    public String toString(){
        return KName;
    }


}
