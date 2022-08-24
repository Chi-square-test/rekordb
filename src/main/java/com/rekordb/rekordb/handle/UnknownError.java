package com.rekordb.rekordb.handle;



import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UnknownError {

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
