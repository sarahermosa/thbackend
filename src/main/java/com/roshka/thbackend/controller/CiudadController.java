package com.roshka.thbackend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshka.thbackend.model.dto.CiudadDto;
import com.roshka.thbackend.model.entity.Ciudad;
import com.roshka.thbackend.model.entity.Estado;
import com.roshka.thbackend.service.ICiudadService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/thbackend/v1")
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

    @PostConstruct
    public void init() {
        try {
            // Lee el archivo JSON
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/JSON/ciudades.json")));

            // Convierte el JSON a objetos Java (por ejemplo, utilizando Jackson ObjectMapper)
            ObjectMapper objectMapper = new ObjectMapper();
            List<Ciudad> ciudades = objectMapper.readValue(json, new TypeReference<List<Ciudad>>() {});
            List<Ciudad> ciudades2 = ciudadService.listAll();

            if (ciudades2.isEmpty()){
                for (Ciudad ciudad : ciudades) {
                    ciudadService.guardar_ciudad(ciudad);
                }
                System.out.println("Datos cargados exitosamente desde el JSON.");
            }
        } catch (IOException e) {
            System.err.println("Error al cargar los datos desde el JSON: " + e.getMessage());
        }
    }




//    @PostMapping("/ciudad/agregar")
//    @ResponseStatus(HttpStatus.CREATED)
//    public List<CiudadDto> create(@RequestBody List<CiudadDto> ciudadDtoList){
//
//        List<CiudadDto> ciudadDtoResponseList = new ArrayList<>();
//
//        for (CiudadDto ciudadDto : ciudadDtoList) {
//            ciudadService.guardar_ciudad(ciudadDto);
//            ciudadDtoResponseList.add(ciudadDto);
//        }
//
//        return ciudadDtoResponseList;
//    }


}

