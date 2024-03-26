package com.roshka.thbackend.service;


import com.roshka.thbackend.model.dto.RolesDto;
import com.roshka.thbackend.model.entity.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> listAll();

    Rol save(RolesDto rol);

    Rol findById(Integer id);

    void delete(Rol rol);


}
