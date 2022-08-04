package com.rekordb.rekordb.tag.query;

import com.rekordb.rekordb.tag.TourAndTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourTagRepository extends JpaRepository<TourAndTag, Long> {
}
