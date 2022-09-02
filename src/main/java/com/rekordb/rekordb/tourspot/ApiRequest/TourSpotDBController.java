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
    public ResponseEntity<Object> updateDatabase(){
        return onlyDevWork(externalAPIService::getTourAPIData);
    }

    @GetMapping("/findgoogleplaceid")
    public ResponseEntity<Object> findgoogleid(){
        return onlyDevWork(externalAPIService::findPlaceId);
    }

    @GetMapping("/getggreviews")
    public ResponseEntity<Object> getgooglereviews(){
        return onlyDevWork(externalAPIService::findReview);
    }

    @GetMapping("/dbmigration")
    public ResponseEntity<Object> dbchange(){
        return onlyDevWork(externalAPIService::toMongo);
    }

    @GetMapping("/detailinfoupdate")
    public ResponseEntity<Object> detailUpdate(){
        return onlyDevWork(externalAPIService::saveDetail);
    }

    public ResponseEntity<Object> onlyDevWork(ExternalAPIService.RunnableExc r) {
        try {
            r.run();
            return ResponseEntity.ok().body(ResponseDTO.<String>builder().status(ApiStatus.SUCCESS).data(Collections.singletonList("테스트")).build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }


}
