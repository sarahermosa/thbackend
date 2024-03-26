package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.TecnologiaDto;
import com.roshka.thbackend.model.entity.Tecnologia;

import java.util.List;

public interface ITecnologiaService {
    List<TecnologiaDto> listAll();
    Tecnologia save(Tecnologia tecnologia);

    void delete(Long id);
}
