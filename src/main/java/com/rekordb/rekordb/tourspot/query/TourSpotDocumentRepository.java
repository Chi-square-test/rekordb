package com.rekordb.rekordb.tourspot.query;

import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


public interface TourSpotDocumentRepository extends MongoRepository<TourSpotDocument, SpotId> {
    List<TourSpotDocument> findByTagListIn(Set<Tag> tag);
}
