package com.springsecuritywithjwt.springsecuritywithjwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
}
