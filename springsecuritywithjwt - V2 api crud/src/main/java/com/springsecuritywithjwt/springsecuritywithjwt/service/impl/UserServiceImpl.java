package com.springsecuritywithjwt.springsecuritywithjwt.service.impl;


import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import com.springsecuritywithjwt.springsecuritywithjwt.repository.UserRepository;
import com.springsecuritywithjwt.springsecuritywithjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
            }
        };
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    @Override
    public Page<User> getUserByName(String name, Pageable pageable) {
        return userRepository.findByFirstName(name, pageable);
    }

    @Override
    public User updateUser(Integer id, User user) {
        var existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Integer id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }


}
