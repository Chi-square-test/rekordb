package com.rekordb.rekordb.tourspot.ApiRequest;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.tourspot.domain.TourSpotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/tourspotadmin")
@RequiredArgsConstructor
public class TourSpotDBController {
    private final ExternalAPIService externalAPIService;
    private final TourSpotService tourSpotService;

    @GetMapping("/updatedb")
    public ResponseEntity<ResponseDTO<String>> updateDatabase(){
        try {
            //externalAPIService.getTourAPIData();
            ResponseDTO<String> res = ResponseDTO.<String>builder().status(ApiStatus.SUCCESS).data(Collections.singletonList("테스트")).build();
            return ResponseEntity.ok().body(res);
        } catch (NullPointerException e){
            log.error("서버와 통신이 제대로 이루어지지 않았습니다."+ e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/findgoogleplaceid")
    public ResponseEntity<ResponseDTO<String>> findgoogleid(){
        try {
            //externalAPIService.findPlaceId();
            ResponseDTO<String> res = ResponseDTO.<String>builder().status(ApiStatus.SUCCESS).data(Collections.singletonList("테스트")).build();
            return ResponseEntity.ok().body(res);
        } catch (NullPointerException e){
            log.error("서버와 통신이 제대로 이루어지지 않았습니다."+ e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getggreviews")
    public ResponseEntity<ResponseDTO<String>> getgooglereviews(){
        try {
            //externalAPIService.findReview();
            ResponseDTO<String> res = ResponseDTO.<String>builder().status(ApiStatus.SUCCESS).data(Collections.singletonList("테스트")).build();
            return ResponseEntity.ok().body(res);
        } catch (NullPointerException e){
            log.error("서버와 통신이 제대로 이루어지지 않았습니다."+ e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/dbmigration")
    public ResponseEntity<ResponseDTO<String>> dbchange(){
        try {
            externalAPIService.toMongo();
            ResponseDTO<String> res = ResponseDTO.<String>builder().status(ApiStatus.SUCCESS).data(Collections.singletonList("테스트")).build();
            return ResponseEntity.ok().body(res);
        } catch (NullPointerException e){
            log.error("서버와 통신이 제대로 이루어지지 않았습니다."+ e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
