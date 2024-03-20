package com.roshka.thbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "referenciapersonal")
public class ReferenciaPersonal implements Serializable {

    @Id
    @Column (name = "id_referencia")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El campo no puede estar vacio")
    @Column (name = "nombre")
    private String nombre;

    @NotBlank(message = "El campo no puede estar vacio")
    @Column (name = "relacion")
    private String relacion;

    @NotBlank(message = "El campo no puede estar vacio")
    @Column (name = "telefono")
    private Integer telefono;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, targetEntity = Postulante.class)
    @JoinColumn (name = "id_postulante")
    private int id_postulante;
}
