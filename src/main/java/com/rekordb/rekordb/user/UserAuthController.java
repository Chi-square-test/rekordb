package com.rekordb.rekordb.user;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.user.Execption.DuplicateUserInfoException;
import com.rekordb.rekordb.NullFormDataException;
import com.rekordb.rekordb.user.Execption.NeedLoginException;
import com.rekordb.rekordb.user.Execption.WrongLoginException;
import com.rekordb.rekordb.user.dto.RekorCreateDTO;
import com.rekordb.rekordb.user.dto.RekorLoginDTO;
import com.rekordb.rekordb.user.dto.TokenSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authapi")
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<TokenSet>> signUp(@RequestBody @Valid RekorCreateDTO dto, BindingResult bindingResult){
        checkNull(bindingResult);
        try {
            TokenSet tokenSet = userAuthService.createFromRekor(dto);
            ResponseDTO<TokenSet> res = ResponseDTO.<TokenSet>builder()
                    .status(ApiStatus.SUCCESS)
                    .data(Collections.singletonList(tokenSet))
                    .build();
            return ResponseEntity.ok().body(res);
        }catch (DuplicateUserInfoException e){
            ResponseDTO<TokenSet> responseDTO = ResponseDTO.<TokenSet>builder()
                    .status(ApiStatus.FAIL)
                    .error(e.getMessage())
                    .build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @GetMapping("/create/validation/phone/{phone}")
    public ResponseEntity<ResponseDTO<Object>> validPhone(@PathVariable("phone") String phone){
        try {
            userAuthService.validatePhone(phone);
            ResponseDTO<Object> res = ResponseDTO.builder()
                    .status(ApiStatus.SUCCESS)
                    .build();
            return ResponseEntity.ok().body(res);
        }catch (DuplicateUserInfoException e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                    .status(ApiStatus.FAIL)
                    .build();
            return  ResponseEntity.ok().body(responseDTO);
        }
    }



    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<TokenSet>> signUp(@RequestBody RekorLoginDTO dto){
        try {
            TokenSet tokenSet = userAuthService.loginFromRekor(dto);
            ResponseDTO<TokenSet> res = ResponseDTO.<TokenSet>builder()
                    .status(ApiStatus.SUCCESS)
                    .data(Collections.singletonList(tokenSet))
                    .build();
            return ResponseEntity.ok().body(res);
        }catch (WrongLoginException e){
            ResponseDTO<TokenSet> responseDTO = ResponseDTO.<TokenSet>builder()
                    .status(ApiStatus.FAIL)
                    .error(e.getMessage())
                    .build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDTO<TokenSet>> refreshToken(@RequestHeader("Access-Token") String access, @RequestHeader("Refresh-Token") String refresh){
        try {
            TokenSet tokenSet = userAuthService.tokenRefresh(access,refresh);
            ResponseDTO<TokenSet> res = ResponseDTO.<TokenSet>builder()
                    .status(ApiStatus.SUCCESS)
                    .data(Collections.singletonList(tokenSet))
                    .build();
            return ResponseEntity.ok().body(res);
        }catch (NeedLoginException e){
            log.warn("refresh 토큰 기간 만료 또는 이상 재발급 요청");
            ResponseDTO<TokenSet> responseDTO = ResponseDTO.<TokenSet>builder()
                    .status(ApiStatus.FAIL)
                    .error("다시 로그인 하세요")
                    .build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }
    }

    public static void checkNull(BindingResult bindingResult) throws NullFormDataException {
        if(bindingResult.hasErrors()) {
            String[] errorString =bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toArray(String[]::new);
            String msg =String.join(", ",errorString) + "은(는) 필수값입니다.";
            throw new NullFormDataException(msg);
        }
    }





}
