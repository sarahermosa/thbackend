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
@RequestMapping("/thbackend/v1")
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
    public List<Ciudad> create(@RequestBody List<Ciudad> ciudadtoList){

        List<Ciudad> ciudadList = new ArrayList<>();

        for (Ciudad ciudad: ciudadList) {
            ciudadList.add(ciudad);
        }
        return ciudadList;
    }


}

