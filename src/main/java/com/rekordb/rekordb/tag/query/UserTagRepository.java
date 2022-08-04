package com.rekordb.rekordb.tag.query;

import com.rekordb.rekordb.tag.UserAndTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepository extends JpaRepository<UserAndTag, Long> {
}
