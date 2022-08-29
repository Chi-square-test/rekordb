package com.rekordb.rekordb.tourspot.domain;

import com.rekordb.rekordb.review.Review;
import com.rekordb.rekordb.review.query.ReviewRepository;
import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.query.TagRepository;
import com.rekordb.rekordb.tourspot.ApiRequest.ExternalAPIService;
import com.rekordb.rekordb.tourspot.Exception.SpotDetailAPIErrorException;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.TourSpotDetail;
import com.rekordb.rekordb.tourspot.dto.SortBy;
import com.rekordb.rekordb.tourspot.query.TourSpotDetailRepository;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.user.dto.DetailAndReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourSpotService {
    private final TourSpotDocumentRepository tourSpotDocumentRepository;
    private final TourSpotDetailRepository tourSpotDetailRepository;
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



    public DetailAndReviewDTO getDetailAndReviews(String id){
        SpotId spotId = SpotId.of(id);
        TourSpotDetail detail = tourSpotDetailRepository.findById(spotId).orElseGet(()->externalAPIService.saveTourApiDetail(spotId));
        detail.checkInformContain();
        List<Review> reviews = reviewRepository.findBySpotId(spotId);
        return DetailAndReviewDTO.ConvertToDTO(detail,reviews);
    }

    public List<TourSpotDocument> getRandomSpot(){
        return tourSpotDocumentRepository.random().getMappedResults();
    }







}
