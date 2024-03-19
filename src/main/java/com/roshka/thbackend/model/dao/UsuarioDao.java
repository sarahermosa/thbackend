package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Integer> {
}
