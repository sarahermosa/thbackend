package com.roshka.thbackend.controller;


import com.roshka.thbackend.model.dto.ConvocatoriaDto;
import com.roshka.thbackend.model.dto.FileDto;
import com.roshka.thbackend.model.entity.Convocatoria;
import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.IConvocatoriaService;
import com.roshka.thbackend.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thbackend/v1")
public class ConvocatoriaController {

    @Autowired
    private IConvocatoriaService convocatoriaService;

    @PostMapping("convocatoria")
    public ResponseEntity<?> create(@RequestBody ConvocatoriaDto convocatoriaDto) {
        Convocatoria convocatoriaSave = null;
        try {
            convocatoriaSave = convocatoriaService.save(convocatoriaDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("File Guardado correctamente")
                    .object(convocatoriaDto.builder()
                            .id_convocatoria(convocatoriaSave.getId_convocatoria())
                            .title(convocatoriaSave.getTitle())
                            .description(convocatoriaSave.getDescription())
                            .fecha_fin(convocatoriaSave.getFecha_fin())
                            .fecha_inicio(convocatoriaSave.getFecha_inicio())
                            .link(convocatoriaSave.getLink())
                            .imagenes(convocatoriaSave.getImagenes())
                            .build())
                    .build()
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
