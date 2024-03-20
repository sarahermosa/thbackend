package com.roshka.thbackend.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TecnologiaDto {
    private Long id_tecnologia;
    @NotBlank (message = "El campo no puede estar vacio")
    private String nombre;
}
