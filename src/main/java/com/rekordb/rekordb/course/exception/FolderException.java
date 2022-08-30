package com.rekordb.rekordb.course.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FolderException extends RuntimeException{
    public FolderException(String msg){
        super(msg);
    }
}
