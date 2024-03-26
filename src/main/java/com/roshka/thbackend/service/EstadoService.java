package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.EstadoDto;
import com.roshka.thbackend.model.entity.Estado;

import java.util.List;

public interface EstadoService {
    Estado guardar_estado(Estado estado);

    List<Estado> listAll();

    void eliminar_estado(Estado estado);

    Estado findById(Long id);

}
