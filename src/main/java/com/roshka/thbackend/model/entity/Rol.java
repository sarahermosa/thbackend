package com.roshka.thbackend.model.entity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "rol")
public class Rol implements Serializable {

    @Id
    @Column(name="id_rol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole descripcion;

}
