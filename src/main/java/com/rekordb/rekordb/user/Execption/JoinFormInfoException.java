package com.rekordb.rekordb.user.Execption;

import lombok.NoArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class JoinFormInfoException  extends RuntimeException{
    public JoinFormInfoException(String msg){
        super(msg);
    }
}
