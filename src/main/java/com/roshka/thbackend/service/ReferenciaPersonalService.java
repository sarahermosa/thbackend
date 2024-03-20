package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.ReferenciaPersonalDto;
import com.roshka.thbackend.model.entity.ReferenciaPersonal;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ReferenciaPersonalService {
    @Transactional
    ReferenciaPersonal guardarReferenciaPersonal(ReferenciaPersonalDto referenciaPersonalDto);

    List<ReferenciaPersonal> listAll();

    @Transactional
    void eliminarReferenciaPersonal(ReferenciaPersonal referenciaPersonal);
}
