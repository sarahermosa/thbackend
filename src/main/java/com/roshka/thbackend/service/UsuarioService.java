package com.roshka.thbackend.service;


import com.roshka.thbackend.model.dto.UsuarioDto;
import com.roshka.thbackend.model.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> listAll();

    Usuario save(UsuarioDto usuario);

    Usuario findById(Integer id);

    void delete(Usuario cliente);

    boolean existsById(Integer id);

}
