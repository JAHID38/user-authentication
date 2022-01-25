package com.auth.userauthentication.repository;

import com.auth.userauthentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
}
