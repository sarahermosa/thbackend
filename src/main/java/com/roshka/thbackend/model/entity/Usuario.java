package com.roshka.thbackend.model.entity;

//Id, email, nombre, apellido, password, reset_password, rol_id
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @Column(name="id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(name="email")
    private String email;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellido")
    private String apellido;

    @Column(name="password")
    private String password;

    @Column(name="reset_password")
    private boolean resetPassword;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
}
