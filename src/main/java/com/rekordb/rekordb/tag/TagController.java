package com.rekordb.rekordb.tag;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.tourspot.dto.SpotListDTO;
import com.rekordb.rekordb.user.Execption.DuplicateUserInfoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/spot/{tag}")
    public ResponseEntity<ResponseDTO<?>> findSpotbyTag(@PathVariable("tag") String tag){
        try {
            List<SpotListDTO> dtoList = tagService.getSpotByTag(tag);
            ResponseDTO<SpotListDTO> res = ResponseDTO.<SpotListDTO>builder().status(ApiStatus.SUCCESS)
                    .data(dtoList)
                    .build();
            return ResponseEntity.ok().body(res);
        }catch (NoSuchElementException e) {
            ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                    .error("없는 태그입니다.")
                    .status(ApiStatus.FAIL)
                    .build();
            return  ResponseEntity.ok().body(responseDTO);
        }
    }
}
