package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.BeneficioDao;
import com.roshka.thbackend.model.dto.BeneficioDto;
import com.roshka.thbackend.model.entity.Beneficio;
import com.roshka.thbackend.model.entity.Estado;
import com.roshka.thbackend.service.IBeneficio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficioImplService implements IBeneficio {

    @Autowired
    private BeneficioDao beneficioDao;

    @Override
    public List<Beneficio> listAll() {
        try {
            return beneficioDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Beneficio save(BeneficioDto beneficioDto) {
        try {
            Beneficio beneficio = new Beneficio();
            beneficio.setTitulo(beneficioDto.getTitulo());
            beneficio.setDescripcion(beneficioDto.getDescripcion());
            return beneficioDao.save(beneficio);
        } catch (Exception e) {
            System.err.println("Error al guardar el beneficio: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void eliminar_beneficio(Beneficio beneficio) {
        beneficioDao.delete(beneficio);
    }
    @Override
    public Beneficio update(BeneficioDto beneficioDto) {
        try {
            Optional<Beneficio> optionalBeneficio = beneficioDao.findById(beneficioDto.getId());
            if (optionalBeneficio.isPresent()) {
                Beneficio beneficio = optionalBeneficio.get();
                beneficio.setTitulo(beneficioDto.getTitulo());
                beneficio.setDescripcion(beneficioDto.getDescripcion());
                return beneficioDao.save(beneficio);
            } else {
                System.err.println("El beneficio con ID " + beneficioDto.getId() + " no se encontró");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el beneficio: " + e.getMessage());
            return null;
        }
    }
}
