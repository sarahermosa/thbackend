package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.CiudadDao;
import com.roshka.thbackend.model.dto.CiudadDto;
import com.roshka.thbackend.model.entity.Ciudad;
import com.roshka.thbackend.service.ICiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CiudadImplService implements ICiudadService {

    @Autowired
    private CiudadDao ciudadDao;

    @Transactional
    @Override
    public Ciudad guardar_ciudad(Ciudad ciudad) {
        return ciudadDao.save(ciudad);
    }

    @Override
    public List<Ciudad> listAll() {
        return (List) ciudadDao.findAll();
    }

    @Transactional
    @Override
    public void eliminar_ciudad(Ciudad ciudad) {
        ciudadDao.delete(ciudad);
    }
}
