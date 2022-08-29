package com.rekordb.rekordb.handle;



import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.NullFormDataException;
import com.rekordb.rekordb.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class UnknownError {

    @ExceptionHandler(value = NullFormDataException.class)
    public ResponseEntity<?> NullFormError(NullFormDataException e){
        log.info("null 필드 요청, "+e.getMessage());
        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .status(ApiStatus.FAIL)
                .error(e.getMessage())
                .build();
        return  ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<?> NoElementError(NoSuchElementException e){
        log.error("db에 없는 데이터 요청. 오류 메세지 : "+e);
        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .status(ApiStatus.ERROR)
                .error("잘못된 요청입니다.")
                .build();
        return  ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> unknownError(Exception e){
        log.error("상정하지 않은 오류가 발생함. 오류 메세지 : "+e);
        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .status(ApiStatus.ERROR)
                .error("오류가 발생했습니다.")
                .build();
        return  ResponseEntity.internalServerError().body(responseDTO);
    }




}
