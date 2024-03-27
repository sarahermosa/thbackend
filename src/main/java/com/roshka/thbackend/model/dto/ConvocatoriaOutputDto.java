package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.Tecnologia;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<Tecnologia> tecnologias = new ArrayList<>();

}
