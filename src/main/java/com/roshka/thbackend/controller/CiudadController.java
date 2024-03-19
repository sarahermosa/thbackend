package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.dto.CiudadDto;
import com.roshka.thbackend.model.entity.Ciudad;
import com.roshka.thbackend.service.ICiudadService;
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
            Ciudad ciudadSave = ciudadService.guardar_ciudad(ciudadDto);
            CiudadDto ciudadDtoResponse = CiudadDto.builder()
                    .id(ciudadSave.getId())
                    .nombre(ciudadSave.getNombre())
                    .build();
            ciudadDtoResponseList.add(ciudadDtoResponse);
        }

        return ciudadDtoResponseList;
    }


}

