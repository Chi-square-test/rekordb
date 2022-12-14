package com.rekordb.rekordb.tag;


import com.rekordb.rekordb.tag.query.TagRepository;
import com.rekordb.rekordb.tag.query.UserTagRepository;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.user.domain.userInfo.User;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TourSpotDocumentRepository tourSpotDocumentRepository;
    private final UserTagRepository userTagRepository;
    private static final int AMOUNT_IN_PAGE =30;

    public Page<TourSpotDocument> getSpotByTag(String tagname, int page) throws NoSuchElementException{
        Set<Tag> tag = Collections.singleton(tagRepository.findByTagName(tagname).orElseThrow(NoSuchElementException::new));
        PageRequest pageRequest = PageRequest.of(page, AMOUNT_IN_PAGE);
        return tourSpotDocumentRepository.findByTagListIn(tag,pageRequest);
    }

    public List<Tag> getAllTag(){
        return tagRepository.findAll();
    }

    public void saveUserTag(String userId, List<String> tagList){
        List<Tag> tags= tagList.stream()
                .map(TagId::of)
                .map(tagRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        userTagRepository.save(new UserAndTag(UserId.of(userId),tags));

    }

    public Page<Tag> findTagByName(String name, int page){
        PageRequest pageRequest = PageRequest.of(page,AMOUNT_IN_PAGE);
        return tagRepository.findByEngTagNameContains(name,pageRequest);
    }

    public List<Tag> getUserTag(String userId) throws NoSuchElementException{
        UserAndTag userAndTag = userTagRepository.findById(UserId.of(userId)).orElseThrow(NoSuchElementException::new);
        return userAndTag.getTagList();
    }

    public List<Tag> getUserTag(User user) throws NoSuchElementException{
        UserAndTag userAndTag = userTagRepository.findById(user.getUserId()).orElseThrow(NoSuchElementException::new);
        return userAndTag.getTagList();
    }





}
