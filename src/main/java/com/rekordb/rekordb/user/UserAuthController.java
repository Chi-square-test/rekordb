package com.rekordb.rekordb.user;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.user.Execption.DuplicateUserInfoException;
import com.rekordb.rekordb.user.Execption.NeedLoginException;
import com.rekordb.rekordb.user.Execption.WrongLoginException;
import com.rekordb.rekordb.user.dto.RekorLoginDTO;
import com.rekordb.rekordb.user.dto.RekorSignUpDTO;
import com.rekordb.rekordb.user.dto.TokenSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authapi")
public class UserAuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<?>> signUp(@RequestBody RekorSignUpDTO dto){
        try {
            TokenSet tokenSet = userService.signUpFromRekor(dto);
            ResponseDTO<TokenSet> res = ResponseDTO.<TokenSet>builder()
                    .status(ApiStatus.SUCCESS)
                    .data(Collections.singletonList(tokenSet))
                    .build();
            return ResponseEntity.ok().body(res);
        }catch (DuplicateUserInfoException e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                    .status(ApiStatus.FAIL)
                    .error(e.getMessage())
                    .build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<?>> signUp(@RequestBody RekorLoginDTO dto){
        try {
            TokenSet tokenSet = userService.loginFromRekor(dto);
            ResponseDTO<TokenSet> res = ResponseDTO.<TokenSet>builder()
                    .status(ApiStatus.SUCCESS)
                    .data(Collections.singletonList(tokenSet))
                    .build();
            return ResponseEntity.ok().body(res);
        }catch (WrongLoginException e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                    .status(ApiStatus.FAIL)
                    .error(e.getMessage())
                    .build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDTO<?>> refreshToken(@RequestHeader("Access-Token") String access, @RequestHeader("Refresh-Token") String refresh){
        try {
            TokenSet tokenSet = userService.tokenRefresh(access,refresh);
            ResponseDTO<TokenSet> res = ResponseDTO.<TokenSet>builder()
                    .status(ApiStatus.SUCCESS)
                    .data(Collections.singletonList(tokenSet))
                    .build();
            return ResponseEntity.ok().body(res);
        }catch (NeedLoginException e){
            log.warn("refresh 토큰 기간 만료 또는 이상 재발급 요청");
            ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                    .status(ApiStatus.FAIL)
                    .error("다시 로그인 하세요")
                    .build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }
    }





}
