package com.springsecuritywithjwt.springsecuritywithjwt.repository;


import com.springsecuritywithjwt.springsecuritywithjwt.entity.Role;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByEmail(String email);

    User findByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:name%")
    Page<User> findByFirstName(String name, Pageable pageable);

}
