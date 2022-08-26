package com.rekordb.rekordb.tag.query;

import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.TagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<Tag, TagId> {
}
