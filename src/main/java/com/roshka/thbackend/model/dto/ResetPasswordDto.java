package com.roshka.thbackend.model.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String token;
    private String email;
    private String password;
    private String oldPassword;


}
