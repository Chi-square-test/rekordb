package com.rekordb.rekordb.tag.admin;


import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.query.TagRepository;
import com.rekordb.rekordb.tourspot.domain.RekorCategory;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminTagService {
    private final TagRepository tagRepository;
    private final TourSpotDocumentRepository tourSpotDocumentRepository;

    private Tag addTagWithDefaultCategory(String tagName,RekorCategory category){
        Tag tag = Tag.makeNewTag(tagName,category);
        return tagRepository.save(tag);
    }

    private Tag addTag(String tagName){
        Tag tag = Tag.makeNewTag(tagName);
        return tagRepository.save(tag);
    }

    public void addSpotNewTag(String spotId,List<String> tags){ //리뷰 분석 및 태그 넣을때 쓰일듯.
        SpotId id = SpotId.of(spotId);
        Set<Tag> tagList =tags.stream().map(s ->tagRepository.findByTagName(s).orElseGet(()->addTag(s))).collect(Collectors.toSet());
        tourSpotDocumentRepository.findById(id).ifPresent(
                tourSpotDocument -> tourSpotDocument.addTagList(tagList)
        );

    }



    public void defaultTagToSpot(){
        Map<RekorCategory, Set<Tag>> catMap = new HashMap<>();
        for (RekorCategory cat: RekorCategory.values()) {
            Set<Tag> tagList = new HashSet<>();
            for (String s: cat.getDefaultTagNameList()) {
                tagList.add(tagRepository.findByTagName(s).orElseGet(()->addTagWithDefaultCategory(s,cat)));
            }
            catMap.put(cat,tagList);
        }
        List<TourSpotDocument> spotDocuments = tourSpotDocumentRepository.findAll();
        spotDocuments.forEach(tourSpotDocument -> tourSpotDocument.setTagList(catMap.get(tourSpotDocument.getRekorCategory())));
        log.info(spotDocuments.get(0).getTagList().toString());
        tourSpotDocumentRepository.saveAll(spotDocuments);

    }

}
