package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.Rol;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@ToString
@Builder
public class UsuarioDto implements Serializable {

    private Integer idUsuario;
    private String email;
    private String nombre;
    private String apellido;
    private String password;
    private boolean resetPassword;
    private Set<Rol> roles;

}
