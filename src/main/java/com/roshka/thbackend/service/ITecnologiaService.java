package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.TecnologiaDto;
import com.roshka.thbackend.model.entity.Tecnologia;

import java.util.List;

public interface ITecnologiaService {
    List<TecnologiaDto> listAll();
    Tecnologia save(TecnologiaDto tecnologiaDto);

    void delete(Long id);
}
