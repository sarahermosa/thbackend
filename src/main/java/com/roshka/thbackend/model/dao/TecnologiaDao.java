package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.Tecnologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TecnologiaDao extends JpaRepository<TecnologiaDao, Long> {
}
