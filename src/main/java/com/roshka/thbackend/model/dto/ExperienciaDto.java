package com.roshka.thbackend.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Builder
public class ExperienciaDto implements Serializable {

    private Long id;
    private String cargo;
    private String descripcion;
    private Date fecha_desde;
    private Date fecha_hasta;
    private String empresa;
    private String nombre_referencia;
    private String telefono_referencia;
    private String tipo_experiencia;
}
