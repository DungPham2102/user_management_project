package com.springsecuritywithjwt.springsecuritywithjwt.service;

import com.springsecuritywithjwt.springsecuritywithjwt.dto.JwtAuthenticationResponse;
import com.springsecuritywithjwt.springsecuritywithjwt.dto.RefreshTokenRequest;
import com.springsecuritywithjwt.springsecuritywithjwt.dto.SignUpRequest;
import com.springsecuritywithjwt.springsecuritywithjwt.dto.SigninRequest;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
