package com.roshka.thbackend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshka.thbackend.model.dto.ConvocatoriaDto;
import com.roshka.thbackend.model.dto.ConvocatoriaOutputDto;
import com.roshka.thbackend.model.dto.FileDto;
import com.roshka.thbackend.model.entity.Convocatoria;
import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.IConvocatoriaService;
import com.roshka.thbackend.service.IFileService;
import lombok.SneakyThrows;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;


@RestController
@Validated
@CrossOrigin
@RequestMapping("/thbackend/v1")
public class ConvocatoriaController {

    @Autowired
    private IConvocatoriaService convocatoriaService;

    @PostMapping(value = "convocatoria")
    public ResponseEntity<?> create(@RequestParam("convocatoriaDto") String convocatoriaDto,
                                    @RequestParam("file") MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ConvocatoriaDto dto = mapper.readValue(convocatoriaDto, ConvocatoriaDto.class);
        dto.setFile(file);
        System.out.println(dto);
        convocatoriaService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("convocatoria")
    public  ResponseEntity<?> listConvocatoria() throws Exception {
        List<ConvocatoriaOutputDto> convocatorias = convocatoriaService.listAll();
        return ResponseEntity.ok().body(convocatorias);
    }

    @DeleteMapping("convocatoria/delete/{id}")
    public  ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception {
        convocatoriaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}


