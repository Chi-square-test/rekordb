package com.rekordb.rekordb.tag;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.ResponsePageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/search")
    public ResponseEntity<?> findTag(@RequestParam String name, @RequestParam int page){
        Page<Tag> spotDocuments = tagService.findTagByName(name,page);
        ResponsePageDTO<Tag> res = ResponsePageDTO.<Tag>builder()
                .status(ApiStatus.SUCCESS)
                .currentPage(spotDocuments.getNumber())
                .allPage(spotDocuments.getTotalPages())
                .data(spotDocuments.get().collect(Collectors.toList()))
                .build();
        return ResponseEntity.ok().body(res);
    }



}
