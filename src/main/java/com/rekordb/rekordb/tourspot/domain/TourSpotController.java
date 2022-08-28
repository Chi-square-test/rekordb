package com.rekordb.rekordb.tourspot.domain;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.ResponsePageDTO;
import com.rekordb.rekordb.tourspot.dto.SpotListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/tourspot")
@RequiredArgsConstructor
public class TourSpotController {
    private final TourSpotService tourSpotService;

    @GetMapping
    public ResponseEntity<ResponseDTO<String>> ping(){
        ResponseDTO<String> res = ResponseDTO.<String>builder().status(ApiStatus.SUCCESS).data(Collections.singletonList("테스트")).build();
        return ResponseEntity.ok().body(res);
    }


    @GetMapping("/category")
    public ResponseEntity<?> getSpotBycategory(@RequestParam int cat, @RequestParam int page,@RequestParam String sort){
        Page<TourSpotDocument> spotDocuments = tourSpotService.getSpotByCategory(cat,page,sort);
        ResponsePageDTO<SpotListDTO> res = ResponsePageDTO.<SpotListDTO>builder()
                .status(ApiStatus.SUCCESS)
                .currentPage(spotDocuments.getNumber())
                .allPage(spotDocuments.getTotalPages())
                .data(spotDocuments.get().map(SpotListDTO::new).collect(Collectors.toList()))
                .build();
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findSpot(@RequestParam String text, @RequestParam int page){
        Page<TourSpotDocument> spotDocuments = tourSpotService.findSpot(text,page);
        ResponsePageDTO<SpotListDTO> res = ResponsePageDTO.<SpotListDTO>builder()
                .status(ApiStatus.SUCCESS)
                .currentPage(spotDocuments.getNumber())
                .allPage(spotDocuments.getTotalPages())
                .data(spotDocuments.get().map(SpotListDTO::new).collect(Collectors.toList()))
                .build();
        return ResponseEntity.ok().body(res);
    }


}
