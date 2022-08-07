package com.rekordb.rekordb.tourspot.ApiRequest;

import com.rekordb.rekordb.tourspot.TourSpot;
import com.rekordb.rekordb.tourspot.query.TourSpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(value = ApiKeysProperties.class)
public class ExternalAPIService {

    private final TourSpotRepository repository;
    private final ApiKeysProperties apiKeys;
    private static final String TOUR_URI = "https://apis.data.go.kr/B551011/KorService";
    private static final String GOOGLE_URI = "https://maps.googleapis.com/maps/api/place";
    private RestTemplate restTemplate;


    public void getTourAPIData() throws NullPointerException{
        try {
            restTemplate = ignoreSSL();
        } catch (KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(TOUR_URI)
                .path("/areaBasedList")
                .queryParam("_type","json")
                .queryParam("numOfRows","11834")
                .queryParam("pageNo","1")
                .queryParam("MobileOS","ETC")
                .queryParam("MobileApp","ReKor")
                .queryParam("serviceKey",apiKeys.getTourKey())
                .queryParam("contentTypeId",12)
                .build();
        String url = URLDecoder.decode(builder.toUri().toString(), StandardCharsets.UTF_8);
        ApiSpotResponse response = restTemplate.getForObject(java.net.URI.create(url),ApiSpotResponse.class);
        List<ApiItemDTO> dtos = response.getItems();
        List<TourSpot> spots = dtos.stream().map(ApiItemDTO::apiConvertEntity).collect(Collectors.toList());
        repository.saveAll(spots);
    }

    public void findPlaceId() throws NullPointerException{
        restTemplate= new RestTemplate();
        for (int i = 3; i < 119; i++) {
            PageRequest pageRequest = PageRequest.of(i,100);
            List<TourSpot> spots = repository.findAll(pageRequest).toList();
            List<TourSpot> updateSpots = new ArrayList<>();
            for (TourSpot s: spots) {
                String title = s.getTitle();
                UriComponents builder = UriComponentsBuilder.fromHttpUrl(GOOGLE_URI)
                        .path("/findplacefromtext")
                        .path("/json")
                        .queryParam("input",title)
                        .queryParam("inputtype","textquery")
                        .queryParam("fields","formatted_address,name,rating,place_id")
                        .queryParam("key",apiKeys.getGoogleKey())
                        .build();
                GooglePlaceIdDTO dto = restTemplate.getForObject(builder.toUri(),GooglePlaceIdDTO.class);
                if(!dto.candidates.isEmpty()){
                    s.setGooglePlaceId(dto.getCandidates().get(0).getPlace_id());
                    s.updateRating(dto.getCandidates().get(0).getRating());
                    updateSpots.add(s);
                }
            }
            repository.saveAll(updateSpots);
            log.info(i+"번째 페이지 저장 완료");
        }
    }














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
