package com.springsecuritywithjwt.springsecuritywithjwt.controller;


import com.springsecuritywithjwt.springsecuritywithjwt.dto.JwtAuthenticationResponse;
import com.springsecuritywithjwt.springsecuritywithjwt.dto.RefreshTokenRequest;
import com.springsecuritywithjwt.springsecuritywithjwt.dto.SignUpRequest;
import com.springsecuritywithjwt.springsecuritywithjwt.dto.SigninRequest;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import com.springsecuritywithjwt.springsecuritywithjwt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // đăng ký, không được trùng username
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    // đăng nhập, sau khi đăng nhập sẽ trả về token
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    // refresh lại token bằng cách gửi refresh token
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

}
