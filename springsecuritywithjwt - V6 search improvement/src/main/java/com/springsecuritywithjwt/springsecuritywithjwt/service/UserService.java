package com.springsecuritywithjwt.springsecuritywithjwt.service;

import com.springsecuritywithjwt.springsecuritywithjwt.dto.SearchUserDto;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.Role;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDetailsService userDetailsService();

    Page<User> getAllUsers(Pageable pageable);

    User getUserById(Integer id);

//    List<User> getUserByName(String name);
    Page<User> getUserByName(String name, Pageable pageable);

    User updateUser(Integer id, User user);

    void deleteUser(Integer id);

    void saveCustomerToDatabaseFromExcel(MultipartFile file);

    Page<User> searchUser(SearchUserDto searchUserDto, Pageable pageable);
}
