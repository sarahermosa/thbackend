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
@Table(name = "estudio")
public class Estudio {

    @Id
    @Column(name = "id_estudios")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_estudios;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_inicio")
    private Date fecha_inicio;

    @Column(name = "fecha_fin")
    private Date fecha_fin;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo_estudios")
    private String tipo_estudio;

    @Column(name = "institucion")
    private String institucion;

}
