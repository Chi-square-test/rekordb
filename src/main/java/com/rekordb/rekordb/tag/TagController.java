package com.rekordb.rekordb.tag;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.ResponsePageDTO;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.dto.SpotListDTO;
import com.rekordb.rekordb.user.Execption.DuplicateUserInfoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping()
    public ResponseEntity<ResponseDTO<?>> getAllTag(){
        ResponseDTO<Tag> res = ResponseDTO.<Tag>builder()
                .status(ApiStatus.SUCCESS)
                .data(tagService.getAllTag())
                .build();
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/spot")
    public ResponseEntity<?> findSpotbyTag(@RequestParam String tag, @RequestParam int page){
        try {
            Page<TourSpotDocument> dtoList = tagService.getSpotByTag(tag,page);
            ResponsePageDTO<SpotListDTO> res = ResponsePageDTO.<SpotListDTO>builder()
                    .status(ApiStatus.SUCCESS)
                    .currentPage(dtoList.getNumber())
                    .allPage(dtoList.getTotalPages())
                    .data(dtoList.get().map(SpotListDTO::new).collect(Collectors.toList()))
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
