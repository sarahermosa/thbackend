package com.roshka.thbackend.model.dto;

import com.roshka.thbackend.model.entity.Postulante;

import java.util.ArrayList;
import java.util.List;

public class ConvocatoriaListaPostulanteDto {
    private Long id_convocatoria;
    private String title;
    List<Postulante> postulanteLista = new ArrayList<>();
}
