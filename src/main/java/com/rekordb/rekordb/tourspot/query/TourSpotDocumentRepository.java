package com.rekordb.rekordb.tourspot.query;

import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


public interface TourSpotDocumentRepository extends MongoRepository<TourSpotDocument, SpotId> {
}
