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
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
        postulante.setTecnologias_asignadas(new HashSet<>());
        postulanteDao.save(postulante);
        for(Long tecnologiaId : PostulanteDto.getTecnologiasList()){
            System.out.println(tecnologiaId);
            assignTecnologiaToEmployee(postulante.getId_postulante(), tecnologiaId);
        }
        return postulante;

    }

    @Override
    public Postulante updatePostulante(Postulante postulante) {
        return null;
    }

    @Override
    public void deletePostulante(Long id) {

    }

    @Override
    public Postulante assignTecnologiaToEmployee(Long postulateId, Long tecnlogiaId) {

        Tecnologia tecnologia = tecnologiaDao.findById(tecnlogiaId).get();
        System.out.println(tecnologia);
        Postulante postulante = postulanteDao.findById(postulateId).get();

        Set<Tecnologia> tecnologiaSet = postulante.getTecnologias_asignadas();
        System.out.println(tecnologiaSet);
        tecnologiaSet.add(tecnologia);
        System.out.println("here");
        System.out.println(tecnologiaSet);
        postulante.setTecnologias_asignadas(tecnologiaSet);

        return postulanteDao.save(postulante);
    }


}


