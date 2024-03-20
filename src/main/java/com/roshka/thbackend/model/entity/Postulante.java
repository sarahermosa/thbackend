package com.roshka.thbackend.model.entity;


import jakarta.persistence.*;
import lombok.*;

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

}
