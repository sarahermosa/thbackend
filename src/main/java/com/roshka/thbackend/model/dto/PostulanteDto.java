package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.Estudio;
import com.roshka.thbackend.model.entity.Ciudad;
import com.roshka.thbackend.model.entity.Estado;
import com.roshka.thbackend.model.entity.Experiencia;
import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.entity.Tecnologia;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostulanteDto {

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
    private List<MultipartFile> filesMultipart;
    private List<Experiencia> experiencias;
    private List<Estudio> estudios = new ArrayList<>();


    private List<File> files = new ArrayList<>();
    private Long id_ciudad;
    private Set<Tecnologia> tecnologiasasignadas;
    private List<Long> tecnologiasList;
    private Ciudad ciudad;
    private Long id_estado;
    private Estado estado;
}