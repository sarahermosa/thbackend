package com.roshka.thbackend.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class CiudadDto implements Serializable {

    private Long id;
    private String nombre;
}
