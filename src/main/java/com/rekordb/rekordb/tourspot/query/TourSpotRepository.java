package com.rekordb.rekordb.tourspot.query;

import com.rekordb.rekordb.tourspot.domain.RekorCategory;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpot;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Deprecated
public interface TourSpotRepository extends JpaRepository<TourSpot, SpotId> {

    List<TourSpot> findAllByGooglePlaceIdIsNotNull(PageRequest pageRequest);
    List<TourSpot> findByRekorCategory(RekorCategory rekorCategory,PageRequest pageRequest);

}
