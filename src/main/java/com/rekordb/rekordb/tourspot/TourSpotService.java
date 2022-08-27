package com.rekordb.rekordb.tourspot;

import com.rekordb.rekordb.tourspot.domain.RekorCategory;
import com.rekordb.rekordb.tourspot.domain.TourSpot;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.dto.SortBy;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.tourspot.query.TourSpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourSpotService {
    private final TourSpotDocumentRepository tourSpotDocumentRepository;
    private static final int AMOUNT_IN_PAGE =30;



    public Page<TourSpotDocument> getSpotByCategory(int category, int page, String sortBy){
        Sort sort = SortBy.getByFront(sortBy).getSort();
        RekorCategory rekorCategory = RekorCategory.valueOfIndex(category);
        PageRequest pageRequest = PageRequest.of(page,AMOUNT_IN_PAGE);
        return tourSpotDocumentRepository.findByRekorCategory(rekorCategory,pageRequest,sort);
    }

    public Page<TourSpotDocument> findSpotByName(String name, int page){
        PageRequest pageRequest = PageRequest.of(page,AMOUNT_IN_PAGE);
        return tourSpotDocumentRepository.findByTitleContains(name,pageRequest,Sort.by(Sort.Direction.ASC, "rating"));
    }



}
