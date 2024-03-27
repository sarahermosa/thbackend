package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.Convocatoria;
import com.roshka.thbackend.model.entity.Postulante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConvocatoriaListaPostulanteDto {
    Convocatoria convocatoria;
    List<Postulante> postulanteLista = new ArrayList<>();
}
