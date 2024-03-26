package com.roshka.thbackend.controller;


import com.roshka.thbackend.model.dto.ExperienciaDto;
import com.roshka.thbackend.model.entity.Experiencia;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.IExperiencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/thbackend/v1")
public class ExperienciaController {

    @Autowired
    private IExperiencia experienciaService;

    @GetMapping("experiencia/lista")
    public ResponseEntity<?> showAll() {
        List<Experiencia> getList = experienciaService.listAll();
        if (getList == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(getList)
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping(value = "experiencia/agregar")
    public ResponseEntity<?> create(@RequestBody ExperienciaDto experienciaDto) {
        Experiencia experienciaSave = null;
        try {
            experienciaSave = experienciaService.save(experienciaDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(ExperienciaDto.builder()
                            .id(experienciaSave.getId())
                            .cargo(experienciaSave.getCargo())
                            .empresa(experienciaSave.getEmpresa())
                            .fecha_desde(experienciaSave.getFecha_desde())
                            .fecha_hasta(experienciaSave.getFecha_hasta())
                            .descripcion(experienciaSave.getDescripcion())
                            .tipo_experiencia(experienciaSave.getTipo_experiencia())
                            .nombre_referencia(experienciaSave.getNombre_referencia())
                            .telefono_referencia(experienciaSave.getTelefono_referencia())
                            .build()).build()

                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}