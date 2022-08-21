package com.rekordb.rekordb.user.Execption;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NeedLoginException extends RuntimeException{
    public NeedLoginException(String msg){
        super(msg);
    }
}
