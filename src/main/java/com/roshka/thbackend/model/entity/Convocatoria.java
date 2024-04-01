package com.roshka.thbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "convocatoria")
public class Convocatoria implements Serializable {

    @Id
    @Column(name = "id_convocatoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_convocatoria;
    @Column(name = "titulo")
    private String title;
    @Column(name = "descripcion")
    @NotEmpty
    @Lob
    private String description;
    @Column(name = "fecha_inicio")
    private Date fecha_inicio;
    @Column(name = "fecha_fin")
    private Date fecha_fin;
    @Column(name = "link")
    private String link="convocatoria/"+id_convocatoria;

    @Column(name = "imagedata")
    private String imageData;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "convocatoria_tecnologias",
            joinColumns = @JoinColumn(name = "id_convocatoria", referencedColumnName = "id_convocatoria"),
            inverseJoinColumns = @JoinColumn(name = "id_tecnologias", referencedColumnName = "id_tecnologia"))
    private Set<Tecnologia> tecnologiasasignadas = new HashSet<>();


}
