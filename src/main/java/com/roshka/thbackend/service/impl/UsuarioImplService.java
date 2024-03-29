package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.UsuarioDao;
import com.roshka.thbackend.model.dto.UsuarioDto;
import com.roshka.thbackend.model.entity.Usuario;
import com.roshka.thbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioImplService implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public List<Usuario> listAll() {
        return (List) usuarioDao.findAll();
    }

    @Transactional
    @Override
    public Usuario save(UsuarioDto clienteDto) {
        Usuario usuario = Usuario.builder()
                .idUsuario(clienteDto.getIdUsuario())
                .email(clienteDto.getEmail())
                .nombre(clienteDto.getNombre())
                .apellido(clienteDto.getApellido())
                .password(clienteDto.getPassword())
                .resetPassword(clienteDto.isResetPassword())
                .rol(clienteDto.getRol())
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

}
