package com.roshka.thbackend.service;


import com.roshka.thbackend.model.dto.CiudadDto;
import com.roshka.thbackend.model.entity.Ciudad;

import java.util.List;

public interface ICiudadService {

    Ciudad guardar_ciudad(Ciudad ciudad);

    List<Ciudad> listAll();

    void eliminar_ciudad(Ciudad ciudad);
}
