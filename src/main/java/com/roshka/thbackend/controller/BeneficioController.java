package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.dto.BeneficioDto;
import com.roshka.thbackend.model.entity.Beneficio;
import com.roshka.thbackend.service.IBeneficio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/thbackend/v1/beneficio")
public class BeneficioController {

    @Autowired
    IBeneficio ibeneficio;

    @GetMapping
    public ResponseEntity<List<Beneficio>> listarBeneficios() {
        List<Beneficio> beneficios = ibeneficio.listAll();
        if (beneficios != null) {
            return ResponseEntity.ok(beneficios);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Beneficio> guardarBeneficio(@RequestBody BeneficioDto beneficioDto) {
        Beneficio beneficioGuardado = ibeneficio.save(beneficioDto);
        if (beneficioGuardado != null) {
            return ResponseEntity.ok(beneficioGuardado);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficio> actualizarBeneficio(@PathVariable Long id, @RequestBody BeneficioDto beneficioDto) {
        beneficioDto.setId(id); // Establecer el ID del beneficio DTO
        Beneficio beneficioActualizado = ibeneficio.update(beneficioDto);
        if (beneficioActualizado != null) {
            return ResponseEntity.ok(beneficioActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBeneficio(@PathVariable Long id) {
        Beneficio beneficio = new Beneficio();
        beneficio.setId(id);
        ibeneficio.eliminar_beneficio(beneficio);
        return ResponseEntity.noContent().build();
        
    }
}

