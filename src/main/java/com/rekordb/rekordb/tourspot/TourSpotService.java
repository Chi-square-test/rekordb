package com.rekordb.rekordb.tourspot;

import com.rekordb.rekordb.tourspot.domain.TourSpot;
import com.rekordb.rekordb.tourspot.query.TourSpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourSpotService {
    private final TourSpotRepository repository;



}
