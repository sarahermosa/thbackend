package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.Ciudad;
import org.springframework.data.repository.CrudRepository;

public interface CiudadDao extends CrudRepository<Ciudad, Long> {
}
