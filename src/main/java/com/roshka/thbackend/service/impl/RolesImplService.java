package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.RolDao;
import com.roshka.thbackend.model.dto.RolesDto;
import com.roshka.thbackend.model.entity.Rol;
import com.roshka.thbackend.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolesImplService implements RolService {

    @Autowired
    private RolDao rolesDao;

    @Override
    public List<Rol> listAll() {
        return (List) rolesDao.findAll();
    }

    @Transactional
    @Override
    public Rol save(RolesDto rolesDto) {
        Rol usuario = Rol.builder()
                .idRol(rolesDto.getIdRol())
                .descripcion(rolesDto.getDescripcion())
                .build();
        return rolesDao.save(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Rol findById(Integer id) {
        return rolesDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Rol findByName(String descripcion) {
        return rolesDao.findByDescripcion(descripcion);
    }

    @Transactional
    @Override
    public void delete(Rol rol) {
        rolesDao.delete(rol);
    }

    @Override
    public boolean existsById(Integer id) {
        return rolesDao.existsById(id);
    }

}
