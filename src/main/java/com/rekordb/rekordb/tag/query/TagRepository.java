package com.rekordb.rekordb.tag.query;

import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.TagId;
import com.rekordb.rekordb.tourspot.domain.RekorCategory;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends MongoRepository<Tag, TagId> {
    Page<Tag> findByDefaultCategory(RekorCategory category, Pageable pageable);
    Optional<Tag> findByTagName(String name);

    Page<Tag> findByTagNameContains(String name, PageRequest pageRequest);
    List<Tag> findTop100ByEngTagName(String name);
    int countByEngTagName(String name);
}
