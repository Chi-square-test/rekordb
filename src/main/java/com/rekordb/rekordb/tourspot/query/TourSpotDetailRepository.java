package com.rekordb.rekordb.tourspot.query;

import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.TourSpotDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TourSpotDetailRepository extends MongoRepository<TourSpotDetail, SpotId> {
}
