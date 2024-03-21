package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.ExperienciaDao;
import com.roshka.thbackend.model.dto.ExperienciaDto;
import com.roshka.thbackend.model.entity.Ciudad;
import com.roshka.thbackend.model.entity.Experiencia;
import com.roshka.thbackend.service.IExperiencia;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ExperienciaImplService implements IExperiencia {

    @Autowired
    ExperienciaDao experienciaDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Experiencia> listAll() {
        return (List<Experiencia>) experienciaDao.findAll();
    }

    @Transactional
    @Override
    public Experiencia save(ExperienciaDto experienciaDto) {
        Experiencia experiencia = modelMapper.map(experienciaDto, Experiencia.class);
        return experienciaDao.save(experiencia);
    }
}
