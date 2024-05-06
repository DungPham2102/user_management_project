package com.springsecuritywithjwt.springsecuritywithjwt.dto;


import lombok.Data;

@Data
public class RefreshTokenRequest {

    private String token;
}
