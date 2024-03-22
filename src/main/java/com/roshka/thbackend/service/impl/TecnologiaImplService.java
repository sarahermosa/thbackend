package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.TecnologiaDao;
import com.roshka.thbackend.model.dto.TecnologiaDto;
import com.roshka.thbackend.model.entity.Tecnologia;
import com.roshka.thbackend.service.ITecnologiaService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TecnologiaImplService implements ITecnologiaService {

    @Autowired
    private TecnologiaDao tecnologiaDao;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<TecnologiaDto> listAll() {
        return (List) tecnologiaDao.findAll();
    }

    @Override
    public Tecnologia save(TecnologiaDto tecnologiaDto) {
        Tecnologia tecnologia = modelMapper.map(tecnologiaDto, Tecnologia.class);
        return tecnologiaDao.save(tecnologia);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tecnologiaDao.deleteById(id);
    }
}
