package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficioDao extends JpaRepository<Beneficio,Long> {
}
