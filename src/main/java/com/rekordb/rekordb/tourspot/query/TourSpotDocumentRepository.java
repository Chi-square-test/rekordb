package com.rekordb.rekordb.tourspot.query;

import com.rekordb.rekordb.course.dto.SpotWithCheck;
import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tourspot.domain.RekorCategory;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public interface TourSpotDocumentRepository extends MongoRepository<TourSpotDocument, SpotId> {
    Page<TourSpotDocument> findByTagListIn(Set<Tag> tag, PageRequest pageRequest);

    Page<TourSpotDocument> findByRekorCategory(RekorCategory category, PageRequest pageRequest, Sort sort);

    Page<TourSpotDocument> findByEngTitleContainsOrTagListIn(String title,Set<Tag> tag, PageRequest pageRequest,Sort sort);

    ArrayList<TourSpotDocument> findTop50BySpotIdNotIn(List<SpotId> ids);

    @Aggregation(pipeline={
            "{$match:{\"images.0\":{$nin:[\"\"]}}}",
            "{$sample:{size:10}}"
    })
    AggregationResults<TourSpotDocument> randomWithImage();

    List<TourSpotDocument> findAllByGooglePlaceIdIsNotNull(PageRequest pageRequest);

    List<TourSpotDocument> findAllByEngAddressNullAndAddress_Addr1IsNotNull();
    int countByEngAddressNullAndAddress_Addr1IsNotNull();
    List<TourSpotDocument> findTop100ByEngTitleIsNull();
    int countByEngTitleIsNull();
    List<TourSpotDocument> findTop100ByEngAddressNullAndAddress_Addr1IsNotNull();

    @Aggregation(pipeline={
            "{$match:{\"rekorCategory\":?0}}",
            "{$sample:{size:6}}"
    })
    AggregationResults<TourSpotDocument> similar(RekorCategory rekorCategory);

    @Aggregation(pipeline={"{$sample:{size:20}}"})
    AggregationResults<TourSpotDocument> pureRandom();


}
