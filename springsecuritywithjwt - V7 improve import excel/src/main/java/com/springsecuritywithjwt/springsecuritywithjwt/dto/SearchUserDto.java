package com.springsecuritywithjwt.springsecuritywithjwt.dto;

import com.springsecuritywithjwt.springsecuritywithjwt.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SearchUserDto {
    private String firstName;

    private String lastName;

    private String email;

}
