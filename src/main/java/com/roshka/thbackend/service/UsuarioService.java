package com.roshka.thbackend.service;


import com.roshka.thbackend.model.dto.UsuarioDto;
import com.roshka.thbackend.model.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listAll();

    Usuario findByEmail(String email);

    Usuario save(UsuarioDto usuario);

    Usuario findById(Integer id);

    void delete(Usuario usuario);

    boolean existsById(Integer id);

    Usuario forgotPass(String email);

    String resetPass(Optional<Usuario> userOptional, String password);

    String generateToken();

    boolean isTokenExpired(final LocalDateTime tokenCreationDate);
}
