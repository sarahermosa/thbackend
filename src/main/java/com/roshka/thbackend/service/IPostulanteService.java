package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.Postulante;
import org.apache.el.stream.Optional;

import java.io.IOException;
import java.util.List;

public interface IPostulanteService {
    public List<Postulante> listAll();
    public Postulante savePostulante(PostulanteDto postulanteDto) throws IOException;
    Postulante findByID(Long id_postulante);
    public void deletePostulante(Long id);

    Postulante updatePostulante(Long id, PostulanteDto postulanteDto);

    public Postulante assignTecnologiaToPostulante(Long postulateId, Long tecnlogiaId);

    public Postulante  assignCityToPostulante(Long postulateId, Long city);

    Postulante assignEstadoToPostulante(Long postulateId, Long estadoId);

    List<Postulante> buscarPorNombre(String nombre);

//    Postulante buscarPorNumeroDocumento(String numeroDocumento);
//
    List<Postulante> buscarPorApellido(String apellido);
//
    List<Postulante> buscarPorEstado(Long idEstado);
}
