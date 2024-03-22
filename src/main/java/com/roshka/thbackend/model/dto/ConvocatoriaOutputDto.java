package com.roshka.thbackend.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConvocatoriaOutputDto {

    private Long id_convocatoria;
    private String title;
    private String description;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String link;
    private String file_path;

}
