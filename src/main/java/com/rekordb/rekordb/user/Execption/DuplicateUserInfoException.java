package com.rekordb.rekordb.user.Execption;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateUserInfoException extends RuntimeException{

   public DuplicateUserInfoException(String msg){
        super(msg);
    }
}
