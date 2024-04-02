package com.roshka.thbackend.service;


import com.roshka.thbackend.model.dto.UsuarioDto;
import com.roshka.thbackend.model.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public interface UsuarioService {
    List<Usuario> listAll();

    Usuario save(UsuarioDto usuario);

    Usuario findById(Integer id);

    void delete(Usuario usuario);

    boolean existsById(Integer id);

    String forgotPass(String email);

    String resetPass(String token, String password);

    String generateToken();

    boolean isTokenExpired(final LocalDateTime tokenCreationDate);
}
