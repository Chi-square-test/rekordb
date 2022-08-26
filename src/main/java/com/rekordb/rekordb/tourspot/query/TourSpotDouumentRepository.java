package com.rekordb.rekordb.tourspot.query;

import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TourSpotDouumentRepository extends MongoRepository<TourSpotDocument, SpotId> {
}
