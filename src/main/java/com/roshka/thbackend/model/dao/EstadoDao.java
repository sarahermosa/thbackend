package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.Estado;
import org.springframework.data.repository.CrudRepository;

public interface EstadoDao extends CrudRepository<Estado, Long> {
}
