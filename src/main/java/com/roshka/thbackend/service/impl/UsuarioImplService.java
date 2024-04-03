package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.UsuarioDao;
import com.roshka.thbackend.model.dto.UsuarioDto;
import com.roshka.thbackend.model.entity.Usuario;
import com.roshka.thbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioImplService implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    private static final long EXPIRE_TOKEN=2;
    
    @Override
    public List<Usuario> listAll() {
        return (List) usuarioDao.findAll();
    }

    @Transactional
    @Override
    public Usuario save(UsuarioDto usuarioDto) {
        Usuario usuario = Usuario.builder()
                .idUsuario(usuarioDto.getIdUsuario())
                .email(usuarioDto.getEmail())
                .nombre(usuarioDto.getNombre())
                .apellido(usuarioDto.getApellido())
                .password(usuarioDto.getPassword())
                .roles(usuarioDto.getRoles())
                .build();
        return usuarioDao.save(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findById(Integer id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Usuario usuario) {
        usuarioDao.delete(usuario);
    }

    @Override
    public boolean existsById(Integer id) {
        return usuarioDao.existsById(id);
    }
    
    public Usuario forgotPass(String email){
        Optional<Usuario> userOptional = usuarioDao.findByEmail(email);

        Usuario user=userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());

        user=usuarioDao.save(user);


        return user;
    }

    public String resetPass(Optional<Usuario> userOptional, String password){

        Usuario user = userOptional.get();

        user.setPassword(password);
        user.setToken(null);
        user.setTokenCreationDate(null);

        usuarioDao.save(user);

        return "Your password successfully updated.";
    }

    public String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    public boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >=EXPIRE_TOKEN;
    }

}
