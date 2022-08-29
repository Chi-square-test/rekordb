package com.rekordb.rekordb.tourspot.Exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpotDetailAPIErrorException extends RuntimeException{
    public SpotDetailAPIErrorException(String msg){
        super(msg);
    }
}
