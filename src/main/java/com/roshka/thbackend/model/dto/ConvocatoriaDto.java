package com.roshka.thbackend.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.roshka.thbackend.model.entity.Convocatoria;
import com.roshka.thbackend.model.entity.Estudio;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String link;
    private MultipartFile file;

    private List<Convocatoria> convocatorias = new ArrayList<>();
}
