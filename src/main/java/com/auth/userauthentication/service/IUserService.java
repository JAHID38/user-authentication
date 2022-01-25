package com.auth.userauthentication.service;

import com.auth.userauthentication.entity.User;
import com.auth.userauthentication.entity.UserRole;

import java.util.List;

public interface IUserService {
    User saveUser (User user);
    List<User> getAllUser();
    User getByUserId (Long id);
    User getByUsername (String username);
    void addUserRole(UserRole userRole);
    void deleteByUserId(Long id);
}
