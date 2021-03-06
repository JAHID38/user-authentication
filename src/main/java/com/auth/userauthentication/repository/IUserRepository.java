package com.auth.userauthentication.repository;

import com.auth.userauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);
}
