package com.rekordb.rekordb.tag.admin;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.tag.dto.SpotTagDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/tagadmin")
@RequiredArgsConstructor
public class AdminTagController {

    private final AdminTagService adminTagService;



    @GetMapping("/setdefaulttag")
    public ResponseEntity<ResponseDTO<?>> setDefault(){
        try {
            adminTagService.defaultTagToSpot();
            ResponseDTO<String> res = ResponseDTO.<String>builder().status(ApiStatus.SUCCESS).data(Collections.singletonList("테스트")).build();
            return ResponseEntity.ok().body(res);
        } catch (NullPointerException e){
            log.error("서버와 통신이 제대로 이루어지지 않았습니다."+ e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/setspottag")
    public ResponseEntity<ResponseDTO<?>> setSpotTags(@RequestBody SpotTagDTO dto){
        try {
            adminTagService.addSpotNewTag(dto.getSpotId(),dto.getTags());
            ResponseDTO<String> res = ResponseDTO.<String>builder().status(ApiStatus.SUCCESS).data(Collections.singletonList("테스트")).build();
            return ResponseEntity.ok().body(res);
        } catch (NullPointerException e){
            log.error("서버와 통신이 제대로 이루어지지 않았습니다."+ e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
