package com.rekordb.rekordb.tourspot;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.tourspot.ApiRequest.ExternalAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/api/tourspot")
@RequiredArgsConstructor
public class TourSpotController {
    private final ExternalAPIService externalAPIService;

    @GetMapping
    public ResponseEntity<ResponseDTO<String>> ping(){
        ResponseDTO<String> res = ResponseDTO.<String>builder().status(ApiStatus.SUCCESS).data(Collections.singletonList("테스트")).build();
        return ResponseEntity.ok().body(res);
    }

}
