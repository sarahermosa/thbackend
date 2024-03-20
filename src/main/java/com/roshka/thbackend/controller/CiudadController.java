package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.dto.CiudadDto;
import com.roshka.thbackend.model.entity.Ciudad;
import com.roshka.thbackend.service.ICiudadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CiudadController {

    @Autowired
    private ICiudadService ciudadService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/ciudades")
    public ResponseEntity<?> listarCiudades() {
        List<Ciudad> ciudades = ciudadService.listAll();
            return ResponseEntity.ok().body(ciudades);
    }

    @PostMapping("/ciudad/agregar")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CiudadDto> create(@RequestBody List<CiudadDto> ciudadDtoList){

        List<CiudadDto> ciudadDtoResponseList = new ArrayList<>();

        for (CiudadDto ciudadDto : ciudadDtoList) {
            System.out.println(ciudadDto);
            ciudadService.guardar_ciudad(ciudadDto);
            ciudadDtoResponseList.add(ciudadDto);
        }

        return ciudadDtoResponseList;
    }


}

