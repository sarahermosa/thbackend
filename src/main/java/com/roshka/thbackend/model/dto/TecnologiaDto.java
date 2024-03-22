package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.Postulante;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@Builder
public class TecnologiaDto {
    private Long id_tecnologia;
    private String nombre;
//    private Set<Postulante> postulanteSet = new HashSet<>();

}
