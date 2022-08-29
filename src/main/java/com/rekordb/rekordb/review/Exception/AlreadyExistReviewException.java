package com.rekordb.rekordb.review.Exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyExistReviewException extends RuntimeException{
    public AlreadyExistReviewException(String msg){
        super(msg);
    }
}
