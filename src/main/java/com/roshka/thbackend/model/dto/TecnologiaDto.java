package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.Postulante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TecnologiaDto {
    private Long id_tecnologia;
    private String nombre;
}
