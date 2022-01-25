package com.auth.userauthentication.controller;

import com.auth.userauthentication.entity.User;
import com.auth.userauthentication.entity.UserRole;
import com.auth.userauthentication.repository.IUserRepository;
import com.auth.userauthentication.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final IUserService userService;

    @GetMapping("/list/user")
    public ResponseEntity getAllUser()
    {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("user/add")
    public ResponseEntity saveUser(@RequestBody User user)
    {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/user/add").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("user-role/add")
    public ResponseEntity saveUserRole(@RequestBody UserRole userRole)
    {
        userService.addUserRole(userRole);
        return ResponseEntity.ok().build();
    }

}
