package com.roshka.thbackend.model.dto;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@Builder
@Table(name = "files")
public class FileDto {

    private Long id_files;
    private String linkToFile;
    private String file_type;
}


