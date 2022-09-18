package com.rekordb.rekordb.user.query;

import com.rekordb.rekordb.user.domain.userInfo.PhoneNumber;
import com.rekordb.rekordb.user.domain.userInfo.User;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UserId> {
    boolean existsByPhoneNumber(PhoneNumber num);
    boolean existsByNickName(String name);
    User findByPhoneNumber(PhoneNumber num);
    Optional<User> findByNickName(String name);

}
