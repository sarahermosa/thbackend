package com.roshka.thbackend.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferenciaPersonalDto {
    private Long id_referencia_personal;
    @NotBlank(message = "El campo no puede estar vacio")
    private String nombre;
    @NotBlank(message = "El campo no puede estar vacio")
    private String relacion;
    @NotNull(message = "El campo no puede estar vacio")
    private Integer telefono;


    private Long id_postulante; //Revisar si esta bien el id

    public Long getId_postulante(){
        return id_postulante;
    }
    public void setId_postulante(Long id_postulante){
        this.id_postulante = id_postulante;
    }
}
