package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.PostulanteDao;
import com.roshka.thbackend.model.dao.TecnologiaDao;
import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.dto.TecnologiaDto;
import com.roshka.thbackend.model.entity.Postulante;
import com.roshka.thbackend.model.entity.Tecnologia;
import com.roshka.thbackend.service.IPostulanteService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import java.util.Set;

@Service
public class PostulanteImpService implements IPostulanteService {

    @Autowired
    private PostulanteDao postulanteDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TecnologiaDao tecnologiaDao;

    @Override
    public List<Postulante> listAll() {
        return  (List) postulanteDao.findAll();
    }

    @Override
    @Transactional
    public Postulante savePostulante(PostulanteDto PostulanteDto) {
        System.out.println(PostulanteDto);
        Postulante postulante = modelMapper.map(PostulanteDto, Postulante.class);
        postulante.setTecnologiasasignadas(new HashSet<>());
        postulanteDao.save(postulante); //create a new postulante without tecnologias

        for(Long tecnologiaId : PostulanteDto.getTecnologiasList()){
            System.out.println(tecnologiaId);
            assignTecnologiaToEmployee(postulante.getId_postulante(), tecnologiaId);
        }
        //after this postulante it is saved with tecnologias
        return postulante;

    }

    @Override
    public Postulante findByID(Long id_postulante) {
        return postulanteDao.findById(id_postulante).orElse(null);
    }

    @Override
    public void deletePostulante(Long id) {

    }

    @Override
    public Postulante updatePostulante(Long id, PostulanteDto postulanteDto) {
        Optional<Postulante> optionalPostulante = postulanteDao.findById(id);

        if (optionalPostulante.isPresent()) {
            Postulante postulante = optionalPostulante.get();

            postulante.setNombre(postulanteDto.getNombre());
            postulante.setApellido(postulanteDto.getApellido());
            postulante.setComentario_rrhh(postulanteDto.getComentario_rrhh());
            postulante.setCorreo(postulanteDto.getCorreo());
            postulante.setDireccion(postulanteDto.getDireccion());
            postulante.setNro_telefono(postulanteDto.getNro_telefono());
            postulante.setNacionalidad(postulanteDto.getNacionalidad());
            postulante.setEstado_civil(postulanteDto.getEstado_civil());
            postulante.setFecha_nacimiento(postulanteDto.getFecha_nacimiento());
            postulante.setFecha_actualizacion(postulanteDto.getFecha_actualizacion());
            postulante.setFecha_creacion(postulanteDto.getFecha_creacion());
            postulante.setFecha_contratado(postulanteDto.getFecha_contratado());
            postulante.setNivel_ingles(postulanteDto.getNivel_ingles());
            return postulanteDao.save(postulante);
        } else {
            throw new RuntimeException("Error al actualizar el postulante. No se encontró el postulante con ID: " + id);
        }
    }

    @Override
    public Postulante assignTecnologiaToEmployee(Long postulateId, Long tecnlogiaId) {

        Tecnologia tecnologia = tecnologiaDao.findById(tecnlogiaId).get();
        System.out.println(tecnologia);
        Postulante postulante = postulanteDao.findById(postulateId).get();

        Set<Tecnologia> tecnologiaSet = postulante.getTecnologiasasignadas();
        System.out.println(tecnologiaSet);
        tecnologiaSet.add(tecnologia);
        System.out.println("here");
        System.out.println(tecnologiaSet);
        postulante.setTecnologiasasignadas(tecnologiaSet);
        return postulanteDao.save(postulante);
    }
}
