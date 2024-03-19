package com.roshka.thbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "referenciapersonal")
public class ReferenciaPersonal {

    @Id
    @Column (name = "id_referencia")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "nombre")
    private String nombre;

    @Column (name = "relacion")
    private String relacion;

    @Column (name = "telefono")
    private Integer telefono;

    @ManyToOne
    @JoinColumn
    @Column (name = "postulante_id")
    private int postulante_id;
}
