package com.roshka.thbackend.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class EstadoDto {
    private Long id_estado;
    private String estado;
}
