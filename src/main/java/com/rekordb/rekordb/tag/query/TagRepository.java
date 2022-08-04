package com.rekordb.rekordb.tag.query;

import com.rekordb.rekordb.tag.Tag;
import com.rekordb.rekordb.tag.TagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, TagId> {
}
