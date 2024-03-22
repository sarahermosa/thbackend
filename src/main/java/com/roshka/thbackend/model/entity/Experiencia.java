package com.roshka.thbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "experiencia")
public class Experiencia {

    @Id
    @Column(name = "id_experiencia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "cargo")
    private String cargo;

    @NotBlank
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_desde")
    private Date fecha_desde;

    @Column(name = "fecha_hasta")
    private Date fecha_hasta;

    @NotBlank
    @Column(name = "empresa")
    private String empresa;

    @NotBlank
    @Column(name = "referente")
    private String nombre_referencia;

    @NotBlank
    @Column(name = "telefono_referente")
    private String telefono_referencia;

    @NotBlank
    @Column(name = "tipo_experiencia")
    private String tipo_experiencia;
    //VALORES POSIBLES : TRABAJO NORMAL - PASANTIA
}
