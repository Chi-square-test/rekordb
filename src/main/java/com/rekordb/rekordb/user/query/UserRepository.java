package com.rekordb.rekordb.user.query;

import com.rekordb.rekordb.user.domain.PhoneNumber;
import com.rekordb.rekordb.user.domain.User;
import com.rekordb.rekordb.user.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
    boolean existsByPhoneNumber(PhoneNumber num);
    boolean existsByNickName(String name);
    User findByPhoneNumber(PhoneNumber num);

}
