package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.Postulante;

import java.util.List;

public interface IPostulanteService {
    public List<Postulante> listAll();
    public Postulante savePostulante(PostulanteDto postulanteDto);
    Postulante findByID(Long id_postulante);
    public void deletePostulante(Long id);

    Postulante updatePostulante(Long id, PostulanteDto postulanteDto);

    public Postulante assignTecnologiaToEmployee(Long postulateId, Long tecnlogiaId);


}
