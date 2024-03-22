package com.roshka.thbackend.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Postulante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id_postulante;
    private String nombre;
    private String apellido;
    private String comentario_rrhh;
    private String correo;
    private String direccion;
    private String nro_telefono;
    private String nacionalidad;
    private String estado_civil;
    private String fecha_nacimiento;
    private String fecha_actualizacion;
    private String fecha_creacion;
    private String fecha_contratado;
    private String nivel_ingles;

    @OneToMany(cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();




    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "postulantesitos_tecnologias",
            joinColumns = @JoinColumn(name = "id_postulantes", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_tecnologias", referencedColumnName = "id_tecnologia"))
    private Set<Tecnologia> tecnologiasasignadas = new HashSet<>();


}
