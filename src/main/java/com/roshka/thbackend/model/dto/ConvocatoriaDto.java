package com.roshka.thbackend.model.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@Builder
@Table(name = "files")
public class ConvocatoriaDto {

    private Long id_convocatoria;
    private String title;
    private String description;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String link;
    private String imagenes;

}
