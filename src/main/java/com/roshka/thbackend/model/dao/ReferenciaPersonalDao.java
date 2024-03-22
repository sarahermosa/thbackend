package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.ReferenciaPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenciaPersonalDao extends JpaRepository<ReferenciaPersonal, Long> {
}
