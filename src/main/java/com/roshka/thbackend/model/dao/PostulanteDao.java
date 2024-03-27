package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.Postulante;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostulanteDao extends CrudRepository<Postulante, Long> {
    List<Postulante> findByNombreContaining(String nombre);

    Postulante findByNumeroDocumento(String numeroDocumento);

    List<Postulante> findByApellidoContaining(String apellido);

    List<Postulante> findByEstadoContaining(Long idEstado);

    List<Postulante> findByEstadoId(Long idEstado);
}
