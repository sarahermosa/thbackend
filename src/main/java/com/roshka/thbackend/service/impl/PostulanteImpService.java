package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.PostulanteDao;
import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.Postulante;
import com.roshka.thbackend.service.IPostulanteService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostulanteImpService implements IPostulanteService {

    @Autowired
    private PostulanteDao postulanteDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Postulante> listAll() {
        return null;
    }

    @Override
    @Transactional
    public Postulante savePostulante(PostulanteDto PostulanteDto) {
        Postulante postulante = new Postulante();
        postulante = modelMapper.map(PostulanteDto, Postulante.class);
        return postulanteDao.save(postulante);

    }

    @Override
    public Postulante updatePostulante(Postulante postulante) {
        return null;
    }

    @Override
    public void deletePostulante(Long id) {

    }
}
