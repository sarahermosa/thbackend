package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.ExperienciaDao;
import com.roshka.thbackend.model.dto.ExperienciaDto;
import com.roshka.thbackend.model.entity.Experiencia;
import com.roshka.thbackend.service.IExperiencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ExperienciaImplService implements IExperiencia {

    @Autowired
    ExperienciaDao experienciaDao;

    @Override
    public List<Experiencia> listAll() {
        return (List<Experiencia>) experienciaDao.findAll();
    }

    @Transactional
    @Override
    public Experiencia save(ExperienciaDto experienciaDto) {
        Experiencia experiencia  = Experiencia.builder()
                .id(experienciaDto.getId())
                .cargo(experienciaDto.getCargo())
                .empresa(experienciaDto.getEmpresa())
                .fecha_desde(experienciaDto.getFecha_desde())
                .fecha_hasta(experienciaDto.getFecha_hasta())
                .descripcion(experienciaDto.getDescripcion())
                .tipo_experiencia(experienciaDto.getTipo_experiencia())
                .nombre_referencia(experienciaDto.getNombre_referencia())
                .telefono_referencia(experienciaDto.getTelefono_referencia())
                .build();
        return experienciaDao.save(experiencia);
    }

    @Override
    public Experiencia findById(Long id) {
        return  experienciaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Experiencia experiencia) {
        experienciaDao.delete(experiencia);
    }

    @Override
    public boolean existsById(Long id) {
        return experienciaDao.existsById(id);
    }
}
