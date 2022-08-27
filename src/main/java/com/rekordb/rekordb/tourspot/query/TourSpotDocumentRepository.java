package com.rekordb.rekordb.tourspot.query;

import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tourspot.domain.RekorCategory;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;


public interface TourSpotDocumentRepository extends MongoRepository<TourSpotDocument, SpotId> {
    Page<TourSpotDocument> findByTagListIn(Set<Tag> tag, PageRequest pageRequest);

    Page<TourSpotDocument> findByRekorCategory(RekorCategory category, PageRequest pageRequest, Sort sort);

    Page<TourSpotDocument> findByTitleContainsOrTagListIn(String title,Set<Tag> tag, PageRequest pageRequest,Sort sort);





}
