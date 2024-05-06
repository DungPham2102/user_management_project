package com.springsecuritywithjwt.springsecuritywithjwt.service.impl;


import com.springsecuritywithjwt.springsecuritywithjwt.dto.SearchUserDto;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.Role;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import com.springsecuritywithjwt.springsecuritywithjwt.repository.UserRepository;
import com.springsecuritywithjwt.springsecuritywithjwt.service.ImportExportService;
import com.springsecuritywithjwt.springsecuritywithjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImportExportService importExportService;

    // lấy thông tin user từ database
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

    // hiện ra tất cả user
    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // hiện ra user theo id
    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    // hiện ra user theo tên
    @Override
    public Page<User> getUserByName(String name, Pageable pageable) {
        return userRepository.findByFirstName(name, pageable);
    }

    // cập nhật thông tin user
    @Override
    public User updateUser(Integer id, User user) {
        var existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    // xóa user theo id
    @Override
    public void deleteUser(Integer id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    // lưu dữ liệu từ file excel vào database
    @Override
    public void saveCustomerToDatabaseFromExcel(MultipartFile file) {
        if(importExportService.isValidExcelFile(file)){
            try {
                // lấy dữ liệu từ file excel
                List<User> users = importExportService.getUsersDataFromExcel(file.getInputStream());
                // lưu dữ liệu vào database
                userRepository.saveAll(users);
            } catch (IOException e) {
                throw new RuntimeException("The file is not a valid excel file");
            }
        }
    }

    @Override
    public Page<User> searchUser(SearchUserDto searchUserDto, Pageable pageable) {
        return userRepository.search(searchUserDto, pageable);
    }


}
