package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.ReferenciaPersonalDao;
import com.roshka.thbackend.model.dto.ReferenciaPersonalDto;
import com.roshka.thbackend.model.entity.ReferenciaPersonal;
import com.roshka.thbackend.service.ReferenciaPersonalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReferenciaPersonalServiceImpl implements ReferenciaPersonalService {

    private ReferenciaPersonalDao referenciaPersonalDao;


    @Transactional
    @Override
    public ReferenciaPersonal guardarReferenciaPersonal(ReferenciaPersonalDto referenciaPersonalDto) {
        ReferenciaPersonal referenciaPersonal = ReferenciaPersonal.builder()
                .id(referenciaPersonalDto.getId_referencia_personal())
                .nombre(referenciaPersonalDto.getNombre())
                .relacion(referenciaPersonalDto.getRelacion())
                .telefono(referenciaPersonalDto.getTelefono())
                .build();
        return referenciaPersonalDao.save(referenciaPersonal);
    }

    @Override
    public List<ReferenciaPersonal> listAll() {
        return referenciaPersonalDao.findAll();
    }

    @Transactional
    @Override
    public void eliminarReferenciaPersonal(ReferenciaPersonal referenciaPersonal) {
        referenciaPersonalDao.delete(referenciaPersonal);
    }
}
