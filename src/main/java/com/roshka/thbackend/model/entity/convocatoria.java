package com.roshka.thbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "files")
public class convocatoria implements Serializable {

    @Id
    @Column(name = "id_convocatoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_convocatoria;
    @Column(name = "titulo")
    private String title;
    @Column(name = "descripcion")
    private String description;
    @Column(name = "fecha_inicio")
    private Date fecha_inicio;
    @Column(name = "fecha_fin")
    private Date fecha_fin;
    @Column(name = "link")
    private String link;
    @Column(name = "imagenes")
    private String imagenes;

}
