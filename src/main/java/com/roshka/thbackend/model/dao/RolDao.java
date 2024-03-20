package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolDao extends JpaRepository<Rol, Integer> {
    Rol findByDescripcion(String name);

}
