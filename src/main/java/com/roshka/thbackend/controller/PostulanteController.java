package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.dto.FileDto;
import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.entity.Postulante;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.IFileService;
import com.roshka.thbackend.service.IPostulanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/thbackend/v1")
public class PostulanteController {

    @Autowired
    private IPostulanteService postulanteService;

    @PostMapping("postulante")
   public Postulante createPostulante(@RequestBody PostulanteDto postulante){
        return postulanteService.savePostulante(postulante);
    }
}






