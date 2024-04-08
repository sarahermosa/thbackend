package com.roshka.thbackend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshka.thbackend.model.dto.EstadoDto;
import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.Ciudad;
import com.roshka.thbackend.model.entity.Estado;
import com.roshka.thbackend.model.entity.Postulante;
import com.roshka.thbackend.service.EstadoService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin
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

    @PostMapping("estado")
    public ResponseEntity<?> agregarEstado(@RequestBody EstadoDto estadoDto) {
        try {
            System.out.println(estadoDto.toString());
            Estado estado = modelMapper.map(estadoDto, Estado.class);
            estado = estadoService.guardar_estado(estado);
            return ResponseEntity.status(HttpStatus.CREATED).body(estado);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostConstruct
    public void init() {
        try {
            // Lee el archivo JSON
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/JSON/estados.json")));

            // Convierte el JSON a objetos Java (por ejemplo, utilizando Jackson ObjectMapper)
            ObjectMapper objectMapper = new ObjectMapper();
            List<Estado> estados = objectMapper.readValue(json, new TypeReference<List<Estado>>() {});
            List<Estado> estados2 = estadoService.listAll();

            if (estados2.isEmpty()){
                for (Estado estado : estados) {
                    estadoService.guardar_estado(estado);
                }
                System.out.println("Datos cargados exitosamente desde el JSON.");
            }





        } catch (IOException e) {
            System.err.println("Error al cargar los datos desde el JSON: " + e.getMessage());
        }
    }

}
