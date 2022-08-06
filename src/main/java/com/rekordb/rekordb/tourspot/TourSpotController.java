package com.rekordb.rekordb.tourspot;

import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.tourspot.ApiRest.TourAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/tourspot")
@RequiredArgsConstructor
public class TourSpotController {
    private final TourAPIService tourAPIService;
    private final TourSpotService tourSpotService;

    @GetMapping("/updatedb")
    public ResponseEntity<ResponseDTO<String>> updateDatabase(){
        try {
            //tourAPIService.getTourAPIData(); 일회성 기능이므로 일단 봉인
            ResponseDTO<String> res = ResponseDTO.<String>builder().data(Collections.singletonList("테스트")).build();
            return ResponseEntity.ok().body(res);
        } catch (NullPointerException e){
            log.error("서버와 통신이 제대로 이루어지지 않았습니다."+ e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }


    }
}
