package com.rekordb.rekordb;

import lombok.NoArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class NullFormDataException extends RuntimeException{
    public NullFormDataException(String msg){
        super(msg);
    }
}
