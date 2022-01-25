package com.auth.userauthentication.service;

import com.auth.userauthentication.entity.Role;
import com.auth.userauthentication.entity.User;
import com.auth.userauthentication.entity.UserRole;
import com.auth.userauthentication.repository.IRoleRepository;
import com.auth.userauthentication.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private final IRoleRepository iRoleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return iUserRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return iUserRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public User getByUserId(Long id) {
        return iUserRepository.findById(id).orElse(null);
    }

    public Role getByRoleId(Long id)
    {
        return iRoleRepository.findById(id).orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException(username +" : NOT FOUND");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public void addUserRole(UserRole userRole)
    {
        User user = getByUserId(userRole.getUser_id());
        Role role = getByRoleId(userRole.getRole_id());
        user.getRole().add(role);
    }

    @Override
    public void deleteByUserId(Long id) {
        iUserRepository.deleteById(id);
    }
}
