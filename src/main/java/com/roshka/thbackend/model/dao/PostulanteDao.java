package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.entity.Postulante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostulanteDao extends CrudRepository<Postulante, Long> {
    List<Postulante> findByFilesContaining(File file);
}
