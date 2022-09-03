package com.rekordb.rekordb.tourspot.ApiRequest;

import com.rekordb.rekordb.review.Review;
import com.rekordb.rekordb.review.query.ReviewRepository;
import com.rekordb.rekordb.tourspot.domain.RekorCategory;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpot;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.CommonItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.DetailItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.ImageItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.TourSpotDetail;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.query.TourSpotDetailRepository;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.tourspot.query.TourSpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(value = ApiKeysProperties.class)
public class ExternalAPIService {

    private final TourSpotRepository tourSpotRepository;

    private final ReviewRepository reviewRepository;

    private final TourSpotDocumentRepository tourSpotDocumentRepository;

    private final TourSpotDetailRepository tourSpotDetailRepository;

    private final ApiKeysProperties apiKeys;
    private static final String TOUR_URI = "https://apis.data.go.kr/B551011/KorService";
    private static final String GOOGLE_URI = "https://maps.googleapis.com/maps/api/place";
    private RestTemplate restTemplate;

    private UriComponentsBuilder componentsBuilder(String endPoint,String contentId,int typeId) {
        return componentsBuilder(endPoint,contentId)
                .queryParam("contentTypeId",typeId);


    }

    private UriComponentsBuilder componentsBuilder(String endPoint,String contentId) {
        return UriComponentsBuilder.fromHttpUrl(TOUR_URI)
                .path(endPoint)
                .queryParam("_type", "json")
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "ReKor")
                .queryParam("serviceKey", apiKeys.getTourKey())
                .queryParam("contentId", contentId);
    }

    public void getTourAPIData() throws NullPointerException{
//        try {
//            restTemplate = ignoreSSL();
//        } catch (KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        UriComponents builder = UriComponentsBuilder.fromHttpUrl(TOUR_URI)
//                .path("/areaBasedList")
//                .queryParam("_type","json")
//                .queryParam("numOfRows","11834")
//                .queryParam("pageNo","1")
//                .queryParam("MobileOS","ETC")
//                .queryParam("MobileApp","ReKor")
//                .queryParam("serviceKey",apiKeys.getTourKey())
//                .queryParam("cat3","A05020100")
//                .build();
//        String url = URLDecoder.decode(builder.toUri().toString(), StandardCharsets.UTF_8);
//        ApiSpotResponse response = restTemplate.getForObject(java.net.URI.create(url),ApiSpotResponse.class);
//        List<ApiItemDTO> dtos = response.getItems();
//        List<TourSpot> spots = dtos.stream().map(ApiItemDTO::apiConvertEntity).collect(Collectors.toList());
//        tourSpotRepository.saveAll(spots);
    }
    //@Scheduled(fixedDelay = 30000)
    public void saveDetail(){
        List<SpotId> alreadyHasDetail = tourSpotDetailRepository.findAll().stream().map(TourSpotDetail::getSpotId).collect(Collectors.toList());
        ArrayList<TourSpotDocument> documents = tourSpotDocumentRepository.findTop50BySpotIdNotIn(alreadyHasDetail);
        log.info(String.valueOf(documents.size()));
        documents.forEach(this::saveTourApiDetail);
    }


    public TourSpotDetail saveTourApiDetail(TourSpotDocument document) { //detail 없는건 이 메서드 전에 체크해야함.
        //log.info("tour api에 정보를 요청합니다.");
        try {
            restTemplate = ignoreSSL();
        } catch (KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SpotId spotId = document.getSpotId();
        int typeId = document.getTypeId();
        String id = spotId.getId();
        TourSpotDetail detail = TourSpotDetail.emptyDetail(spotId);
        ignoringExc(() ->getTourApiDetailCommon(id,typeId,detail));
        ignoringExc(() ->getTourApiDetailInfo(id,typeId,detail));
        ignoringExc(() ->getTourApiDetailIntro(id,typeId,detail));
        ignoringExc(() ->getTourApiDetailImage(id,detail));
        return tourSpotDetailRepository.save(detail);
    }

    private void getTourApiDetailCommon(String id, int typeId, TourSpotDetail detail) throws NullPointerException{
        UriComponents builder = componentsBuilder("/detailCommon",id,typeId)
                .queryParam("defaultYN","Y")
                .queryParam("overviewYN","Y")
                .build();
        String url = URLDecoder.decode(builder.toUri().toString(), StandardCharsets.UTF_8);
        ApiCommonResponse response = restTemplate.getForObject(java.net.URI.create(url),ApiCommonResponse.class);
        CommonItem commonItem  = response.getItems().get(0);
        commonItem.parsingUrl();
        detail.saveDetailCommon(commonItem);
    }

    private void getTourApiDetailInfo(String id, int typeId, TourSpotDetail detail) throws NullPointerException{
        UriComponents builder = componentsBuilder("/detailInfo",id,typeId).build();
        String url = URLDecoder.decode(builder.toUri().toString(), StandardCharsets.UTF_8);
        ApiInfoResponse response = restTemplate.getForObject(java.net.URI.create(url),ApiInfoResponse.class);
        List<DetailItem> detailItems = response.getItems();
        detailItems.forEach(DetailItem::removeTag);
        detail.saveDetailInfo(detailItems);
    }

    private void getTourApiDetailIntro(String id, int typeId, TourSpotDetail detail) throws NullPointerException{
        UriComponents builder = componentsBuilder("/detailIntro",id,typeId).build();
        String url = URLDecoder.decode(builder.toUri().toString(), StandardCharsets.UTF_8);
        ApiIntroResponse response = restTemplate.getForObject(java.net.URI.create(url),ApiIntroResponse.class);
        Map<String,String>  intro = response.getItems().get(0);
        intro.values().removeIf(String::isBlank);
        intro.replaceAll((k, v) -> v = TourSpotDetail.replaceTag(v));
        detail.saveDetailIntro(intro);
    }

    private void getTourApiDetailImage(String id,  TourSpotDetail detail) throws NullPointerException{
        UriComponents builder = componentsBuilder("/detailImage",id)
                .queryParam("imageYN","Y")
                .queryParam("subImageYN","Y")
                .build();
        String url = URLDecoder.decode(builder.toUri().toString(), StandardCharsets.UTF_8);
        ApiImageResponse response = restTemplate.getForObject(java.net.URI.create(url),ApiImageResponse.class);
        List<ImageItem> imageItems = response.getItems();
        detail.saveDetailImage(imageItems);
    }


    public void findPlaceId() throws NullPointerException{
//        restTemplate= new RestTemplate();
//        for (int i = 1; i < 70; i++) {
//            PageRequest pageRequest = PageRequest.of(i,100);
//            List<TourSpot> spots = tourSpotRepository.findByRekorCategory(RekorCategory.FOOD,pageRequest);
//            List<TourSpot> updateSpots = new ArrayList<>();
//            for (TourSpot s: spots) {
//                String title = s.getTitle();
//                UriComponents builder = UriComponentsBuilder.fromHttpUrl(GOOGLE_URI)
//                        .path("/findplacefromtext")
//                        .path("/json")
//                        .queryParam("input",title)
//                        .queryParam("inputtype","textquery")
//                        .queryParam("key",apiKeys.getGoogleKey())
//                        .build();
//                GooglePlaceIdDTO dto = restTemplate.getForObject(builder.toUri(),GooglePlaceIdDTO.class);
//                if(!dto.candidates.isEmpty()){
//                    s.setGooglePlaceId(dto.getCandidates().get(0).getPlace_id());
//                    s.updateRating(dto.getCandidates().get(0).getRating());
//                    updateSpots.add(s);
//                }
//            }
//            tourSpotRepository.saveAll(updateSpots);
//            log.info(i+"번째 페이지 저장 완료");
//        }
    }

    public String findReview() throws NullPointerException{
        restTemplate= new RestTemplate();
        for (int i = 0; i <1; i++) {
            PageRequest pageRequest = PageRequest.of(i,1);
            List<TourSpot> spots = tourSpotRepository.findAllByGooglePlaceIdIsNotNull(pageRequest);
            List<Review> googleReviews = new ArrayList<>();
            for (TourSpot s: spots) {
                String placeId = s.getGooglePlaceId();
                UriComponents builder = UriComponentsBuilder.fromHttpUrl(GOOGLE_URI)
                        .path("/details")
                        .path("/json")
                        .queryParam("place_id",placeId)
                        .queryParam("fields","reviews")
                        .queryParam("key",apiKeys.getGoogleKey())
                        .build();
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept-Language","ko-kr");
                log.info(builder.toString());
                ResponseEntity<String> dto = restTemplate.exchange(builder.toUri(), HttpMethod.GET,new HttpEntity<>(headers),String.class);
                log.info(dto.getBody());
                return dto.getBody();
                //ResponseEntity<GoogleReviewDTO> dto = restTemplate.exchange(builder.toUri(), HttpMethod.GET,new HttpEntity<>(headers),GoogleReviewDTO.class);

//                if(dto.getBody().result.reviews!=null){
//                    for (GoogleReviewDTO.review rev: dto.getBody().result.reviews) {
//                        googleReviews.add(Review.googleReviewToDB(rev,s.getSpotId()));
//                    }
//                }
            }
            //reviewRepository.saveAll(googleReviews);
            //log.info(i+"번째 페이지 저장 완료");
        }
        return null;
    }

    public void toMongo() {
//        List<TourSpot> spotEntitys = tourSpotRepository.findAll();
//        List<TourSpotDocument> spotDocuments = spotEntitys.stream().map(TourSpotDocument::new).collect(Collectors.toList());
//        tourSpotDocumentRepository.saveAll(spotDocuments);
    }



    public static void ignoringExc(RunnableExc r) {
        try { r.run(); } catch (Exception e) {log.error(e.getMessage()); }
    }

    @FunctionalInterface public interface RunnableExc { void run() throws Exception; }















    private static RestTemplate ignoreSSL() throws KeyStoreException, KeyManagementException, NoSuchAlgorithmException {
        TrustStrategy strategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null,strategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext,new NoopHostnameVerifier());
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(client);
        factory.setConnectTimeout(30*1000);
        factory.setReadTimeout(30*1000);

        return new RestTemplate(factory);


    }




}
