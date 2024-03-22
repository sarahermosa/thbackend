package com.roshka.thbackend.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.roshka.thbackend.model.Estudio;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Postulante")
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

    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    private Ciudad ciudad;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Experiencia> experiencias = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Estudio> estudios = new ArrayList<>();

}
