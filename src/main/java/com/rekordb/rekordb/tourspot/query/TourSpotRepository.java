package com.rekordb.rekordb.tourspot.query;

import com.rekordb.rekordb.tourspot.SpotId;
import com.rekordb.rekordb.tourspot.TourSpot;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface TourSpotRepository extends JpaRepository<TourSpot, SpotId> {

    List<TourSpot> findAllByGooglePlaceIdIsNotNull(PageRequest pageRequest);

}
