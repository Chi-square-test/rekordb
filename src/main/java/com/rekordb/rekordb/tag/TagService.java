package com.rekordb.rekordb.tag;


import com.rekordb.rekordb.tag.query.TagRepository;
import com.rekordb.rekordb.tourspot.dto.SpotListDTO;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TourSpotDocumentRepository tourSpotDocumentRepository;

    public List<SpotListDTO> getSpotByTag(String tagname) throws NoSuchElementException{
        Set<Tag> tag = Collections.singleton(tagRepository.findByTagName(tagname).orElseThrow(NoSuchElementException::new));
        return tourSpotDocumentRepository.findByTagListIn(tag).stream().map(SpotListDTO::new).collect(Collectors.toList());
    }


}
