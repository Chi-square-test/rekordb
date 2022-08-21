package com.rekordb.rekordb.user.Execption;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WrongLoginException extends RuntimeException{

    public WrongLoginException(String msg){
        super(msg);
    }
}
