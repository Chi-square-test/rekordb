package com.rekordb.rekordb.tourspot.domain;

import com.rekordb.rekordb.review.Review;
import com.rekordb.rekordb.review.query.ReviewRepository;
import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.query.TagRepository;
import com.rekordb.rekordb.tourspot.ApiRequest.ExternalAPIService;
import com.rekordb.rekordb.tourspot.Exception.SpotDetailAPIErrorException;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.TourSpotDetail;
import com.rekordb.rekordb.tourspot.dto.SortBy;
import com.rekordb.rekordb.tourspot.dto.SpotListDTO;
import com.rekordb.rekordb.tourspot.query.TourSpotDetailRepository;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.tourspot.dto.DetailAndReviewDTO;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import com.rekordb.rekordb.user.query.UserWishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourSpotService {
    private final TourSpotDocumentRepository tourSpotDocumentRepository;
    private final TourSpotDetailRepository tourSpotDetailRepository;
    private final UserWishListRepository wishListRepository;
    private final ReviewRepository reviewRepository;
    private static final int AMOUNT_IN_PAGE =30;
    private final TagRepository tagRepository;
    private final ExternalAPIService externalAPIService;



    public Page<TourSpotDocument> getSpotByCategory(int category, int page, String sortBy){
        Sort sort = SortBy.getByFront(sortBy).getSort();
        RekorCategory rekorCategory = RekorCategory.valueOfIndex(category);
        PageRequest pageRequest = PageRequest.of(page,AMOUNT_IN_PAGE);
        return tourSpotDocumentRepository.findByRekorCategory(rekorCategory,pageRequest,sort);
    }

    public Page<TourSpotDocument> findSpot(String name, int page){
        PageRequest pageRequest = PageRequest.of(page,AMOUNT_IN_PAGE);
        PageRequest tagPageRequest = PageRequest.of(0,100);
        Set<Tag> tagSet =tagRepository.findByTagNameContains(name,tagPageRequest).toSet();
        return tourSpotDocumentRepository.findByTitleContainsOrTagListIn(name,tagSet,pageRequest,Sort.by(Sort.Direction.DESC, "rating"));
    }



    public DetailAndReviewDTO getDetailAndReviews(String uid,String sid){
        SpotId spotId = SpotId.of(sid);
        TourSpotDocument document = tourSpotDocumentRepository.findById(spotId).orElseThrow();
        TourSpotDetail detail = tourSpotDetailRepository.findById(spotId).orElseGet(()->externalAPIService.saveTourApiDetail(document));
        if(!detail.checkInformContain()){
            tourSpotDetailRepository.delete(detail);
            throw new SpotDetailAPIErrorException();
        }
        List<Review> reviews = reviewRepository.findBySpotId(spotId);
        boolean isReviewed = reviewRepository.existsByUserIdAndSpotId(UserId.of(uid),spotId);
        return DetailAndReviewDTO.convertToDTO(document,detail,reviews,isReviewed);
    }

    public List<SpotListDTO> getRandomSpot(String user){
        UserId userId = UserId.of(user);
        List<TourSpotDocument> documents =tourSpotDocumentRepository.random().getMappedResults();
        List<SpotListDTO> dtos = new ArrayList<>();
        for (TourSpotDocument d:documents) {
           Boolean b = wishListRepository.existsByUserIdAndWishListContains(userId,d);
           SpotListDTO dto = new SpotListDTO(d);
           dto.setIsInWishList(b);
           dtos.add(dto);
        }
        return dtos;
    }







}
