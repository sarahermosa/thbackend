package com.roshka.thbackend.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConvocatoriaDto {

    private Long id_convocatoria;
    private String title;
    private String description;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String link="convocatoria/"+id_convocatoria;
    private MultipartFile file;

}
