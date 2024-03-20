package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.dto.TecnologiaDto;
import com.roshka.thbackend.model.entity.Tecnologia;
import com.roshka.thbackend.service.ITecnologiaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/thbackend/v1")
public class TecnologiaController {
    @Autowired
    private ITecnologiaService tecnologiaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/tecnologia")
    public ResponseEntity<?> listarTecnologias() {
        List<TecnologiaDto> tecnologias = tecnologiaService.listAll();
        return ResponseEntity.ok().body(tecnologias);
    }

    @PostMapping("/tecnologia/agregar")
    @ResponseStatus(HttpStatus.CREATED)
    public List<TecnologiaDto> create(@RequestBody List<TecnologiaDto> tecnologiaDtoList){

        List<TecnologiaDto> tecnologiaDtoResponseList = new ArrayList<>();

        for (TecnologiaDto tecnologiaDto : tecnologiaDtoList) {
            tecnologiaService.save(tecnologiaDto);
            tecnologiaDtoResponseList.add(tecnologiaDto);
        }

        return tecnologiaDtoResponseList;
    }
}
