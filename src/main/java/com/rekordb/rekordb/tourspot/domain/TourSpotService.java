package com.rekordb.rekordb.tourspot.domain;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.rekordb.rekordb.review.ReviewService;
import com.rekordb.rekordb.review.dto.ReviewDTO;
import com.rekordb.rekordb.review.query.ReviewRepository;
import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.TagService;
import com.rekordb.rekordb.tag.query.TagRepository;
import com.rekordb.rekordb.tourspot.ApiRequest.ExternalAPIService;
import com.rekordb.rekordb.tourspot.Exception.SpotDetailAPIErrorException;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.TourSpotDetail;
import com.rekordb.rekordb.tourspot.dto.CheckItem;
import com.rekordb.rekordb.tourspot.dto.SortBy;
import com.rekordb.rekordb.tourspot.dto.SpotListDTO;
import com.rekordb.rekordb.tourspot.query.TourSpotDetailRepository;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.tourspot.dto.DetailAndReviewDTO;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import com.rekordb.rekordb.user.query.UserRepository;
import com.rekordb.rekordb.user.query.UserWishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourSpotService {
    private final TourSpotDocumentRepository tourSpotDocumentRepository;
    private final TourSpotDetailRepository tourSpotDetailRepository;
    private final UserWishListRepository wishListRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private static final int AMOUNT_IN_PAGE =30;
    private final TagRepository tagRepository;
    private final ReviewService reviewService;
    private final ExternalAPIService externalAPIService;
    private final TagService tagService;




    public Page<TourSpotDocument> getSpotByCategory(int category, int page, String sortBy){
        Sort sort = SortBy.getByFront(sortBy).getSort();
        RekorCategory rekorCategory = RekorCategory.valueOfIndex(category);
        PageRequest pageRequest = PageRequest.of(page,AMOUNT_IN_PAGE);
        return tourSpotDocumentRepository.findByRekorCategory(rekorCategory,pageRequest,sort);
    }

    public Page<TourSpotDocument> findSpot(String name, int page){
        PageRequest pageRequest = PageRequest.of(page,AMOUNT_IN_PAGE);
        PageRequest tagPageRequest = PageRequest.of(0,100);
        Set<Tag> tagSet =tagRepository.findByEngTagNameContains(name,tagPageRequest).toSet();
        return tourSpotDocumentRepository.findByEngTitleContainsOrTagListIn(name,tagSet,pageRequest,Sort.by(Sort.Direction.DESC, "rating"));
    }



    public DetailAndReviewDTO getDetailAndReviews(String uid,String sid){
        SpotId spotId = SpotId.of(sid);
        TourSpotDocument document = tourSpotDocumentRepository.findById(spotId).orElseThrow();
        TourSpotDetail detail = tourSpotDetailRepository.findById(spotId).orElseGet(()->externalAPIService.saveTourApiDetail(document));
        if(!detail.checkInformContain()){
            tourSpotDetailRepository.delete(detail);
            throw new SpotDetailAPIErrorException();
        }
        if(detail.isDetailCommonFin()&&(!detail.isHasEngOverview())){
            if(!detail.getCommonItem().checkOverviewNull()){
                translateOverView(detail);
            }
        }
        List<ReviewDTO> reviews = reviewRepository.findBySpotId(spotId).stream()
                .map(review -> ReviewDTO.ConvertToDTO(review,reviewService.getReviewTagList(review)))
                .collect(Collectors.toList());
        for (ReviewDTO d : reviews) {
            if(!d.isFromGoogle()){
                d.setUserTagList(tagService.getUserTag(userRepository.findByNickName(d.getUserName()).orElseThrow()));
            }
        }
        boolean isReviewed = reviewRepository.existsByUserIdAndSpotId(UserId.of(uid),spotId);
        boolean isInWishList = wishListRepository.existsByUserIdAndWishListContains(UserId.of(uid),document);
        return DetailAndReviewDTO.convertToDTO(document,detail,reviews,new CheckItem(isReviewed,isInWishList));
    }

    public void translateOverView(TourSpotDetail detail){
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation =
                translate.translate(
                        detail.getCommonItem().getOverview(),
                        Translate.TranslateOption.sourceLanguage("ko"),
                        Translate.TranslateOption.targetLanguage("en"));
        String res = translation.getTranslatedText();
        //log.info("Translation: "+res);
        detail.setEngOverview(res);
        detail.setHasEngOverview(true);
        tourSpotDetailRepository.save(detail);
    }

    public List<SpotListDTO> getRandomSpotWithHaveImage(String user){
        UserId userId = UserId.of(user);
        List<TourSpotDocument> documents =tourSpotDocumentRepository.randomWithImage().getMappedResults();
        List<SpotListDTO> dtos = new ArrayList<>();
        for (TourSpotDocument d:documents) {
           Boolean b = wishListRepository.existsByUserIdAndWishListContains(userId,d);
           SpotListDTO dto = new SpotListDTO(d);
           dto.setIsWished(b);
           dtos.add(dto);
        }
        return dtos;
    }

    public List<SpotListDTO> getSimilarSpot(String spot){
        SpotId spotId = SpotId.of(spot);
        TourSpotDocument document = tourSpotDocumentRepository.findById(spotId).orElseThrow();
        List<TourSpotDocument> documents =tourSpotDocumentRepository.similar(document.getRekorCategory()).getMappedResults();
        return documents.stream().map(SpotListDTO::new).collect(Collectors.toList());
    }

    public List<SpotListDTO> getPureRandomSpot(){
        List<TourSpotDocument> documents =tourSpotDocumentRepository.pureRandom().getMappedResults();
        return documents.stream().map(SpotListDTO::new).collect(Collectors.toList());
    }









}
