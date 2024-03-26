package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsDto {
    private Integer idUsuario;
    private String email;
    private String nombre;
    private String apellido;
    private String password;
    private Set<Rol> roles;

    public UserDetailsDto(Integer idUsuario, String email, String nombre, String apellido, Set<Rol> roles) {
    }
}
