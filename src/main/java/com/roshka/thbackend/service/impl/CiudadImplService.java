package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.CiudadDao;
import com.roshka.thbackend.model.dto.CiudadDto;
import com.roshka.thbackend.model.entity.Ciudad;
import com.roshka.thbackend.service.ICiudadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CiudadImplService implements ICiudadService {

    @Autowired
    private CiudadDao ciudadDao;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    @Override
    public Ciudad guardar_ciudad(Ciudad ciudad) {
        return ciudadDao.save(ciudad);
    }

    @Override
    public List<Ciudad> listAll() {
        return (List) ciudadDao.findAll();
    }

    @Override
    public Optional<Ciudad> findById(Long id) {
        return ciudadDao.findById(id);
    }

    @Transactional
    @Override
    public void eliminar_ciudad(Ciudad ciudad) {
        ciudadDao.delete(ciudad);
    }
}
