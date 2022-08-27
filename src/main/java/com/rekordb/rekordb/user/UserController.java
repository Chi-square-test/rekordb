package com.rekordb.rekordb.user;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.TagService;
import com.rekordb.rekordb.user.Execption.DuplicateUserInfoException;
import com.rekordb.rekordb.user.dto.RekorJoinInformDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final TagService tagService;

    @GetMapping("/join/validation")
    public ResponseEntity<ResponseDTO<?>> checkJoined(@AuthenticationPrincipal User user){
        ApiStatus status = userService.isUserJoined(user.getUsername()) ?  ApiStatus.SUCCESS : ApiStatus.FAIL;
        ResponseDTO<Object> res = ResponseDTO.builder()
                .status(status)
                .build();
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/join/validation/nickname/{name}")
    public ResponseEntity<ResponseDTO<?>> validName(@PathVariable("name") String name){
        try {
            userService.validateName(name);
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

    @GetMapping("/tag")
    public ResponseEntity<ResponseDTO<?>> getAllTag(@AuthenticationPrincipal User user){
        ResponseDTO<Tag> res = ResponseDTO.<Tag>builder()
                .status(ApiStatus.SUCCESS)
                .data(tagService.getUserTag(user.getUsername()))
                .build();
        return ResponseEntity.ok().body(res);
    }


    @PostMapping("/tag")
    public ResponseEntity<ResponseDTO<?>> saveUserTag(@AuthenticationPrincipal User user, @RequestBody List<String> tagList){
        tagService.saveUserTag(user.getUsername(),tagList);
        ResponseDTO<Object> res = ResponseDTO.builder()
                .status(ApiStatus.SUCCESS)
                .build();
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO<?>> joinData(@AuthenticationPrincipal User user, @RequestBody RekorJoinInformDTO dto){
        try {
            userService.signUpFromRekor(user.getUsername(),dto);
            ResponseDTO<Object> res = ResponseDTO.builder()
                    .status(ApiStatus.SUCCESS)
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


}
