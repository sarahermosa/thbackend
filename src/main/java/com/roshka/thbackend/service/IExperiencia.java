package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.ExperienciaDto;
import com.roshka.thbackend.model.dto.UsuarioDto;
import com.roshka.thbackend.model.entity.Experiencia;
import com.roshka.thbackend.model.entity.Usuario;

import java.util.List;

public interface IExperiencia {

    List<Experiencia> listAll();

    Experiencia save(ExperienciaDto usuario);
}
