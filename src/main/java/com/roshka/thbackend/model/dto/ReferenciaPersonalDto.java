package com.roshka.thbackend.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Data
@ToString
@Builder
@Table (name = "referenciapersonal")
public class ReferenciaPersonalDto {
    private Long id_referencia_personal;
    @NotBlank(message = "El campo no puede estar vacio")
    private String nombre;
    @NotBlank(message = "El campo no puede estar vacio")
    private String relacion;
    @NotNull(message = "El campo no puede estar vacio")
    private Integer telefono;
}
