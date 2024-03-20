package com.roshka.thbackend.model.dto;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@Builder
public class TecnologiaDto {
    private Long id_tecnologia;
    private String nombre;
}
