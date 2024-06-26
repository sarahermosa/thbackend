package com.roshka.thbackend.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.roshka.thbackend.model.entity.Convocatoria;

import java.io.Serializable;
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
public class Postulante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id_postulante;

    private String nombre;


    private String apellido;

    @Column(name = "nro_documento")
    private String nro_documento;

    private String tipo_documento;



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

    private String nombre_ciudad;

    @OneToMany(cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL)
    private List<ReferenciaPersonal> referencia_personal = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    private Ciudad ciudad;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Experiencia> experiencias = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Estudio> estudios = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estado;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "postulantes_tecnologias",
            joinColumns = @JoinColumn(name = "id_postulantes", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_tecnologias", referencedColumnName = "id_tecnologia"))
    private Set<Tecnologia> tecnologiasasignadas = new HashSet<>();

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "id_convocatoria")
    private Convocatoria convocatoria;


}
