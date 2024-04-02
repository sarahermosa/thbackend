package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.Rol;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@ToString
@Builder
public class UsuarioDto implements Serializable {

    private Integer idUsuario;

    @NotEmpty
    private String email;
    private String nombre;
    private String apellido;

    @NotEmpty
    private String password;
    private Set<Rol> roles;

}
