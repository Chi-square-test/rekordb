package com.rekordb.rekordb.user.query;

import com.rekordb.rekordb.user.User;
import com.rekordb.rekordb.user.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserEmail> {

}
