package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.dto.EstadoDto;
import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.Estado;
import com.roshka.thbackend.model.entity.Postulante;
import com.roshka.thbackend.service.EstadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/thbackend/v1")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/estados")
    public ResponseEntity<?> listarEstados(){
        List<Estado> estados = estadoService.listAll();
        return ResponseEntity.ok().body(estados);
    }

    @PostMapping("/estado")
    public Estado create(@RequestBody EstadoDto estadoDto){
        return estadoService.guardar_estado(estadoDto);
    }
}
