package com.roshka.thbackend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshka.thbackend.model.dto.ConvocatoriaDto;
import com.roshka.thbackend.model.dto.ConvocatoriaListaPostulanteDto;
import com.roshka.thbackend.model.dto.ConvocatoriaOutputDto;
import com.roshka.thbackend.model.dto.FileDto;
import com.roshka.thbackend.model.entity.Convocatoria;
import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.entity.Postulante;
import com.roshka.thbackend.model.entity.Tecnologia;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.IConvocatoriaService;
import com.roshka.thbackend.service.IFileService;
import com.roshka.thbackend.service.IPostulanteService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@Validated
@CrossOrigin
@RequestMapping("/thbackend/v1")
public class ConvocatoriaController {

    @Autowired
    private IConvocatoriaService convocatoriaService;

    @Autowired
    private IPostulanteService postulanteService;

    @PostMapping(value = "convocatoria")
    public ResponseEntity<?> create(@RequestParam("convocatoria_info") String convocatoriaDto,
                                    @RequestParam("file") MultipartFile file,
                                    @RequestParam("convocatorias_tecnologias_ids") String convocatorias_tecnologias_ids ) throws IOException {

    try{
        ObjectMapper mapper = new ObjectMapper();
        List<Long> tecnologiasListId = mapper.readValue(convocatorias_tecnologias_ids, mapper.getTypeFactory().constructCollectionType(List.class, Long.class));
        ConvocatoriaDto dto = mapper.readValue(convocatoriaDto, ConvocatoriaDto.class);

        dto.setTecnologias_ids(tecnologiasListId);
        dto.setFile(file);

        convocatoriaService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

    @GetMapping("convocatoria_postulantes/{id}")
    public  ResponseEntity<?> listConvocatoriaPostulantes(@PathVariable Long id) throws Exception {

        try{


        ConvocatoriaListaPostulanteDto output = new ConvocatoriaListaPostulanteDto();
        Convocatoria convocatoria = convocatoriaService.findById(id);
        List<Postulante> filteredPostulantes = new ArrayList<>();
        List<Postulante> lista_de_postulantes = postulanteService.listAll();
        for(Postulante postulante : lista_de_postulantes){
            if(postulante.getConvocatoria().getId_convocatoria().equals(convocatoria.getId_convocatoria())){

                filteredPostulantes.add(postulante);
                System.out.println("Convocatoria de postulante ");
            }else{
                System.out.println("No convocatoria");
            }

        }
        output.setConvocatoria(convocatoria);
        output.setPostulanteLista(filteredPostulantes);
        System.out.println(output);

        return ResponseEntity.ok().body(output);

        }catch (Exception e)
            {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }


    @GetMapping("convocatoria_tecnologia/{id}")
    public  ResponseEntity<?> listConvocatoriaTecnologia(@PathVariable Long id) throws Exception {
        try{
        List<ConvocatoriaOutputDto> output = new ArrayList<>();
        List<ConvocatoriaOutputDto> lista_de_convocatorias = convocatoriaService.listAll();

            lista_de_convocatorias.stream()
                    .filter(convocatoria -> convocatoria.getTecnologias().stream()
                            .anyMatch(tecnologia -> tecnologia.getId_tecnologia() == id))
                    .forEach(output::add);

            return ResponseEntity.ok().body(output);

        }catch (Exception e)
            {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }

}


