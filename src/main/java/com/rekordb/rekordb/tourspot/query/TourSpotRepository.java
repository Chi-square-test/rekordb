package com.rekordb.rekordb.tourspot.query;

import com.rekordb.rekordb.tourspot.SpotId;
import com.rekordb.rekordb.tourspot.TourSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourSpotRepository extends JpaRepository<TourSpot, SpotId> {
}
