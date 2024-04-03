package com.roshka.thbackend.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class BeneficioDto {
    private Long id;
    private String titulo;
    private String descripcion;
}
