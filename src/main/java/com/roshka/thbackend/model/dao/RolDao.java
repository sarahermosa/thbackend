package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.ERole;
import com.roshka.thbackend.model.entity.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RolDao extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByDescripcion(ERole name);

}
