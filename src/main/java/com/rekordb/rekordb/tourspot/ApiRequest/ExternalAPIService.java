package com.rekordb.rekordb.tourspot.ApiRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCursor;
import com.rekordb.rekordb.course.Course;
import com.rekordb.rekordb.course.CourseFolder;
import com.rekordb.rekordb.course.dto.SpotWithCheck;
import com.rekordb.rekordb.course.query.CourseFolderRepository;
import com.rekordb.rekordb.review.Review;
import com.rekordb.rekordb.review.query.ReviewRepository;
import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.UserAndTag;
import com.rekordb.rekordb.tag.query.TagRepository;
import com.rekordb.rekordb.tag.query.UserTagRepository;
import com.rekordb.rekordb.tourspot.ApiRequest.juso.JusoRes;
import com.rekordb.rekordb.tourspot.ApiRequest.test.Example;
import com.rekordb.rekordb.tourspot.ApiRequest.test.GoogleReview;
import com.rekordb.rekordb.tourspot.domain.*;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.CommonItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.DetailItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.ImageItem;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.TourSpotDetail;
import com.rekordb.rekordb.tourspot.query.TourSpotDetailRepository;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.tourspot.query.TourSpotRepository;
import com.rekordb.rekordb.user.domain.userWithSpot.UserWishList;
import com.rekordb.rekordb.user.query.UserWishListRepository;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

import static com.rekordb.rekordb.tag.Tag.makeNewTag;


@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(value = ApiKeysProperties.class)
public class ExternalAPIService {

    private final TourSpotRepository tourSpotRepository;

    private final ReviewRepository reviewRepository;

    private final TourSpotDocumentRepository tourSpotDocumentRepository;

    private final TourSpotDetailRepository tourSpotDetailRepository;

    private final UserTagRepository userTagRepository;

    private final UserWishListRepository userWishListRepository;

    private final CourseFolderRepository courseFolderRepository;

    private final AddTagRepo addTagRepo;

    private final TagRepository tagRepository;

    private final ApiKeysProperties apiKeys;
    private static final String TOUR_URI = "https://apis.data.go.kr/B551011/KorService";
    private static final String GOOGLE_URI = "https://maps.googleapis.com/maps/api/place";
    private static final String JUSO_URI = "https://business.juso.go.kr/addrlink/addrEngApi.do";

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

    //@Scheduled(cron = "0 9 15 18 9 *",zone = "Asia/Seoul")
    public void tagDiffChange(){
        List<TourSpotDocument> documents = tourSpotDocumentRepository.findAll();
        for (TourSpotDocument s: documents) {
            s.getTagList().forEach(tag -> tag.setEngTagName(tagRepository.findById(tag.getTagId()).orElseThrow().getEngTagName()));
        }
        tourSpotDocumentRepository.saveAll(documents);
        log.info("documents tags are updated");

        List<UserAndTag> userAndTags = userTagRepository.findAll();
        for (UserAndTag u: userAndTags) {
            u.getTagList().forEach(tag -> tag.setEngTagName(tagRepository.findById(tag.getTagId()).orElseThrow().getEngTagName()));
        }
        userTagRepository.saveAll(userAndTags);

        log.info("users tags are updated");

        List<UserWishList> userWishLists = userWishListRepository.findAll();
        for (UserWishList u: userWishLists) {
            u.getWishList().forEach(this::updateSpotTagAndAddr);
        }
        userWishListRepository.saveAll(userWishLists);

        log.info("wishlists are updated");

        List<CourseFolder> courseFolders = courseFolderRepository.findAll();
        for (CourseFolder f:courseFolders) {
            for (Course c :f.getCourseList()) {
                c.getSpotList().forEach((spot ->updateSpotTagAndAddr(spot.getDocument())));
            }
        }
        courseFolderRepository.saveAll(courseFolders);
        log.info("course tags are updated");

        log.info("fin");
    }

    private void updateSpotTagAndAddr(TourSpotDocument s){
        s.getTagList().forEach(tag -> tag.setEngTagName(tagRepository.findById(tag.getTagId()).orElseThrow().getEngTagName()));
        s.setEngTitle((tourSpotDocumentRepository.findById(s.getSpotId()).orElseThrow().getEngTitle()));
        EngAddress e = tourSpotDocumentRepository.findById(s.getSpotId()).orElseThrow().getEngAddress();
        s.setEnglishAddr(e.getEngAddr1(),e.getEngAddr2());
    }


    public void translateTitle(){
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        int least = tourSpotDocumentRepository.countByEngTitleIsNull();
        while (least>0){
            List<TourSpotDocument> documents = tourSpotDocumentRepository.findTop100ByEngTitleIsNull();
            List<TourSpotDocument> saveSpot = new ArrayList<>();
            for (TourSpotDocument d:documents) {
                Translation translation =
                        translate.translate(
                                d.getTitle(),
                                Translate.TranslateOption.sourceLanguage("ko"),
                                Translate.TranslateOption.targetLanguage("en"));
                String res = translation.getTranslatedText();
                log.info("Translation: "+res);
                d.setEngTitle(res);
                saveSpot.add(d);

            }
            tourSpotDocumentRepository.saveAll(saveSpot);
            least = tourSpotDocumentRepository.countByEngTitleIsNull();
        }


    }

    @Scheduled(cron = "0 52 21 19 9 *",zone = "Asia/Seoul")
    public void translateTest(){
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation =
                translate.translate(
                        "구글 번역 테스트",
                        Translate.TranslateOption.sourceLanguage("ko"),
                        Translate.TranslateOption.targetLanguage("en"));
        String res = translation.getTranslatedText();
        log.info("Translation: "+res);

    }


    public void translateTag(){
        int least = tagRepository.countByEngTagName("");
        log.info(least+"");
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        while (least>0){
            List<Tag> tags = tagRepository.findTop100ByEngTagName("");
            List<Tag> saveTag = new ArrayList<>();
            for (Tag d:tags) {
                Translation translation =
                        translate.translate(
                                d.getTagName(),
                                Translate.TranslateOption.sourceLanguage("ko"),
                                Translate.TranslateOption.targetLanguage("en"));
                String res = translation.getTranslatedText();
                log.info("Translation: "+res);
                d.setEngTagName(res);
                saveTag.add(d);

            }
            tagRepository.saveAll(saveTag);
            least = tagRepository.countByEngTagName("");
        }
        log.info("fin");
    }


    public void translateLastAddr(){
        int count = tourSpotDocumentRepository.countByEngAddressNullAndAddress_Addr1IsNotNull();
        log.info("남은 집계 수 : " + count);

        Translate translate = TranslateOptions.getDefaultInstance().getService();
        while (count>0){
            List<TourSpotDocument> documents = tourSpotDocumentRepository.findTop100ByEngAddressNullAndAddress_Addr1IsNotNull();
            List<TourSpotDocument> spots = new ArrayList<>();
            for (TourSpotDocument d : documents) {
                Translation translation =
                        translate.translate(
                                d.getAddress().getAddr1(),
                                Translate.TranslateOption.sourceLanguage("ko"),
                                Translate.TranslateOption.targetLanguage("en"));
                String res1 = translation.getTranslatedText();
                translation =
                        translate.translate(
                                d.getAddress().getAddr2(),
                                Translate.TranslateOption.sourceLanguage("ko"),
                                Translate.TranslateOption.targetLanguage("en"));
                String res2 = translation.getTranslatedText();
                log.info("Translation: "+res1+" "+res2);
                d.setEnglishAddr(res1,res2);
                spots.add(d);

            }
            tourSpotDocumentRepository.saveAll(spots);
            count = tourSpotDocumentRepository.countByEngAddressNullAndAddress_Addr1IsNotNull();
        }
        log.info("fin");


    }




    public void convertAddEnglish() {
        restTemplate = new RestTemplate();
        int count = tourSpotDocumentRepository.countByEngAddressNullAndAddress_Addr1IsNotNull();
        log.info("남은 집계 수 : " + count);
        List<TourSpotDocument> documents = tourSpotDocumentRepository.findAllByEngAddressNullAndAddress_Addr1IsNotNull();
        List<TourSpotDocument> saveSpot = new ArrayList<>();
        for (TourSpotDocument d : documents) {
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(JUSO_URI)
                    .queryParam("confmKey", apiKeys.getJusoKey())
                    .queryParam("currentPage", "1")
                    .queryParam("countPerPage", "1")
                    .queryParam("keyword", d.getAddress().getAddr1())
                    .queryParam("resultType", "json")
                    .build();

            JusoRes response = restTemplate.getForObject(builder.toUri(), JusoRes.class);
            try{
                if(Integer.parseInt(response.getResults().getCommon().getTotalCount())>0){
                    d.setEnglishAddr(response.getResults().getJuso().get(0).getRoadAddr(),response.getResults().getJuso().get(0).getSggNm());
                    saveSpot.add(d);
                }
                else{
                    log.info("검색 결과가 없어요");
                }
            }catch (Exception e){
                log.info(e.toString());
            }
        }
        log.info("저장된 개수"+saveSpot.size());
        tourSpotDocumentRepository.saveAll(saveSpot);
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

    public void addTagsFromReviews(){
       List<CopyofReviewAndTags> list = addTagRepo.findAllByTagNotNull();
       int i=0;
        for (CopyofReviewAndTags t:list) {
            SpotId id = SpotId.of(t.getSpot_id());
            Set<Tag> tags = new HashSet<>();
            for (String s: t.getTag()) {
                log.info(String.valueOf(i++));
                Tag tag = tagRepository.findByTagName(s)
                        .orElseGet(()-> tagRepository.save(makeNewTag(s)));
                tags.add(tag);
            }
            log.info(id.getId());
            try{
                TourSpotDocument document = tourSpotDocumentRepository.findById(id).orElseThrow(NoSuchElementException::new);
                document.addTagList(tags);
                tourSpotDocumentRepository.save(document);
            }catch (NoSuchElementException ignored){

            }

        }
    }


    public void findReview() throws NullPointerException {
        restTemplate= new RestTemplate();
        //List<SpotId> already = reviewRepository.findReviewExist();
        int count = tourSpotRepository.countByGooglePlaceIdIsNotNull();
        log.info("남은 집계 수 : "+count);
        for (int i = 0; i <count/100; i++) {
            PageRequest pageRequest = PageRequest.of(i,100);
            List<TourSpot> spots = tourSpotRepository.findAllByGooglePlaceIdIsNotNull(pageRequest);
            for (TourSpot s: spots) {
                List<Review> googleReviews = new ArrayList<>();
                String placeId = s.getGooglePlaceId();
                UriComponents builder = UriComponentsBuilder.fromHttpUrl(GOOGLE_URI)
                        .path("/details")
                        .path("/json")
                        .queryParam("place_id",placeId)
                        .queryParam("fields","reviews")
                        .queryParam("key",apiKeys.getGoogleKey())
                        .build();

                Example dto = restTemplate.getForObject(builder.toUri(), Example.class);
                try{
                    List<GoogleReview> reviews =dto.getResult().getReviews();
//                    int sum = 0;
                    for (GoogleReview r:reviews) {
                        googleReviews.add(Review.googleReviewToDB(r,s.getSpotId()));
                        //sum+=r.getRating();
                    }
                    reviewRepository.saveAll(googleReviews);
                    googleReviews.clear();
//                    int finalSum = sum;
//                    tourSpotDocumentRepository.findById(s.getSpotId()).ifPresent(
//                            document -> {
//                                document.updateRating(finalSum / reviews.size());
//                                tourSpotDocumentRepository.save(document);
//                            }
//                    );
                }catch (NullPointerException e){
                    log.info("정상적인 리뷰 수집 불가로 플레이스 id 제거");
                    s.deleteGooglePlaceId();
                    tourSpotRepository.save(s);
                }


            }
            log.info(i+"번째 페이지 저장 완료");
        }

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
