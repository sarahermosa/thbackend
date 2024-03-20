package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.ConvocatoriaDao;
import com.roshka.thbackend.model.dto.ConvocatoriaDto;
import com.roshka.thbackend.model.entity.Convocatoria;
import com.roshka.thbackend.service.IConvocatoriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvocatoriaImplService implements IConvocatoriaService {
    @Autowired
    private ConvocatoriaDao convocatoriaDao;

    @Override
    public List<Convocatoria> listAll(){
        return (List) convocatoriaDao.findAll();
    }

    @Override
    @Transactional
    public Convocatoria save(ConvocatoriaDto convocatoriaDto) {
        Convocatoria convocatoria = Convocatoria.builder()
                .id_convocatoria(convocatoriaDto.getId_convocatoria())
                .title(convocatoriaDto.getTitle())
                .description(convocatoriaDto.getDescription())
                .fecha_fin(convocatoriaDto.getFecha_fin())
                .fecha_inicio(convocatoriaDto.getFecha_inicio())
                .link(convocatoriaDto.getLink())
                .imagenes(convocatoriaDto.getImagenes())
                .build();
        return convocatoriaDao.save(convocatoria);
    }

    @Override
    public Convocatoria findById(Long id) {
        return convocatoriaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public boolean existsById(ConvocatoriaDto convocatoria) {
        return false;
    }
}
