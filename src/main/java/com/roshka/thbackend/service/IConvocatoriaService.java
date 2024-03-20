package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.ConvocatoriaDto;
import com.roshka.thbackend.model.entity.Convocatoria;

import java.util.List;

public interface IConvocatoriaService {
    List<Convocatoria> listAll();
    Convocatoria save(ConvocatoriaDto convocatoriaDto);
    Convocatoria findById(Long id);


    boolean existsById(Long id);

    void delete(Long id);

    boolean existsById(ConvocatoriaDto convocatoria);
}
