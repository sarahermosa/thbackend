package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.Postulante;

import java.util.List;

public interface IPostulanteService {
    public List<PostulanteDto> listAll();
    public Postulante savePostulante(PostulanteDto postulanteDto);
    public Postulante updatePostulante(Postulante postulante);

    public void deletePostulante(Long id);

    public Postulante assignTecnologiaToEmployee(Long postulateId, Long tecnlogiaId);


}
