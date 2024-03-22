package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.EstadoDao;
import com.roshka.thbackend.model.dto.EstadoDto;
import com.roshka.thbackend.model.entity.Estado;
import com.roshka.thbackend.service.EstadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoImplService implements EstadoService {

    @Autowired
    private EstadoDao estadoDao;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public Estado guardar_estado(EstadoDto estadoDto) {
        Estado estado = modelMapper.map(estadoDto, Estado.class);
        return estadoDao.save(estado);
    }

    @Override
    public List<Estado> listAll() {
        return (List) estadoDao.findAll();
    }

    @Override
    public void eliminar_estado(Estado estado) {
        estadoDao.delete(estado);
    }

    @Override
    public Estado findById(Long id) {
        return estadoDao.findById(id).orElse(null);
    }
}
