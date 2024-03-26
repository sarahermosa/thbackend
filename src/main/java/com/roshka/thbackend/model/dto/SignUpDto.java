package com.roshka.thbackend.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String nombre;
    private String apellido;
    private String password;
    private Set<String> role;

}
