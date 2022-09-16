package com.rekordb.rekordb.tourspot.ApiRequest;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddTagRepo extends MongoRepository<CopyofReviewAndTags,String> {
    List<CopyofReviewAndTags> findAllByTagNotNull();
}
