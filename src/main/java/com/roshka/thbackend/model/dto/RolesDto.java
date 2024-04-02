package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.ERole;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class RolesDto implements Serializable {

    private Integer idRol;
    private ERole descripcion;

}
