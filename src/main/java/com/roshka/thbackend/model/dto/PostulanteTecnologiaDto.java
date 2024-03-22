package com.roshka.thbackend.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostulanteTecnologiaDto {

    private Long id_postulante_tecnologia;

    @NotBlank (message = "El campo no puede estar vacio")
    private String nivel;

    private Long id_postulante;

    public Long getId_postulante(){
        return id_postulante;
    }
    public void setId_postulante(Long id_postulante){
        this.id_postulante = id_postulante;
    }

    private Long id_tecnologia;

    public Long getId_tecnologia(){
        return id_tecnologia;
    }

    public void setId_tecnologia(Long id_tecnologia){
        this.id_tecnologia = id_tecnologia;
    }
}
