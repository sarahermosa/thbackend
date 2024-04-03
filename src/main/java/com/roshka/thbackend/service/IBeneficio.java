package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.BeneficioDto;
import com.roshka.thbackend.model.entity.Beneficio;
import com.roshka.thbackend.model.entity.Estado;

import java.util.List;

public interface IBeneficio {

    List<Beneficio> listAll();
    void eliminar_beneficio(Beneficio beneficio);

    List<String> obtenerNombresBeneficios();

    Beneficio save(BeneficioDto beneficio);
    Beneficio update(BeneficioDto beneficioDto);
}
