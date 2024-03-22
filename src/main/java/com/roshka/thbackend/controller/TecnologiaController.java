package com.roshka.thbackend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshka.thbackend.model.dto.TecnologiaDto;
import com.roshka.thbackend.model.entity.Estado;
import com.roshka.thbackend.model.entity.Tecnologia;
import com.roshka.thbackend.service.ITecnologiaService;
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

    @PostConstruct
    public void init() {
        try {
            // Lee el archivo JSON
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/JSON/tecnologias.json")));

            // Convierte el JSON a objetos Java (por ejemplo, utilizando Jackson ObjectMapper)
            ObjectMapper objectMapper = new ObjectMapper();
            List<Tecnologia> tecnologias = objectMapper.readValue(json, new TypeReference<List<Tecnologia>>() {});

            // Guarda los datos en la base de datos
            for (Tecnologia tecnologia : tecnologias) {
                tecnologiaService.save(tecnologia);
            }

            System.out.println("Datos cargados exitosamente desde el JSON.");

        } catch (IOException e) {
            System.err.println("Error al cargar los datos desde el JSON: " + e.getMessage());
        }
    }
}
