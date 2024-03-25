package com.roshka.thbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshka.thbackend.model.dto.ExperienciaDto;
import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.*;
import com.roshka.thbackend.service.EstadoService;
import com.roshka.thbackend.service.ICiudadService;
import com.roshka.thbackend.service.IPostulanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/thbackend/v1")
public class PostulanteController {

    @Autowired
    private IPostulanteService postulanteService;

    @Autowired
    private ICiudadService ciudadService;

    @Autowired
    private EstadoService estadoService;

    @PostMapping("postulante")
   public ResponseEntity<?> createPostulante(@RequestParam("postulante_info") String postulante,
                                             @RequestParam("files") MultipartFile[] files,
                                             @RequestParam("experiencias") String experiencias,
                                             @RequestParam("estudios") String estudios,
                                             @RequestParam("tecnologias_id") String tecnologiasId)
                                             throws IOException {


        ObjectMapper mapper = new ObjectMapper();
        try{

            PostulanteDto dto = mapper.readValue(postulante, PostulanteDto.class);
            List<Experiencia> experienciasList = mapper.readValue(experiencias, mapper.getTypeFactory().constructCollectionType(List.class, Experiencia.class));
            List<MultipartFile> incomingFiles = Arrays.asList(files);
            List<Estudio> estudiosList = mapper.readValue(estudios, mapper.getTypeFactory().constructCollectionType(List.class, Estudio.class));
            List<Long> tecnologiasListId = mapper.readValue(tecnologiasId, mapper.getTypeFactory().constructCollectionType(List.class, Long.class));
            Optional<Ciudad> ciudad = ciudadService.findById(dto.getId_ciudad());
            Optional<Estado> estado = Optional.ofNullable(estadoService.findById(dto.getId_estado()));

            dto.setFilesMultipart(incomingFiles);
            dto.setExperiencias(experienciasList);
            dto.setEstudios(estudiosList);
            dto.setTecnologiasList(tecnologiasListId);
//            dto.setCiudad(ciudad.get());
//            dto.setEstado(estado.get());

            postulanteService.savePostulante(dto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


        return ResponseEntity.ok().body("Guardado correctamente");
//        Optional<Ciudad> ciudad = ciudadService.findById(postulante.getId_ciudad());
//        Optional<Estado> estado = Optional.ofNullable(estadoService.findById(postulante.getId_estado()));
//
//
//        if (ciudad.isPresent()) {
//            postulante.setCiudad(ciudad.get());
//        }
//
//        if (estado.isPresent()){
//            postulante.setEstado(estado.get());
//        }

//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            PostulanteDto dto = mapper.readValue(postulante, PostulanteDto.class);
//
//            dto.setFiles_cv(file);
//            System.out.println("asdas");
//            postulanteService.savePostulante(dto);
//            return ResponseEntity.ok().body("Guardado correctamente");
//        }catch (Exception e) {
//                // Si ocurre un error, se devuelve un mensaje de error con el código de estado correspondiente
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la entidad: " + e.getMessage());
//            }
    }

    @GetMapping("postulante")
    public ResponseEntity<?> listarPostulantes() {
        List<Postulante> postulantes = postulanteService.listAll();

        if (postulantes.isEmpty()) {
            return ResponseEntity.ok().body("No hay postulantes disponibles en este momento.");
        } else {
            return ResponseEntity.ok().body(postulantes);
        }
    }

    @GetMapping("postulante/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Postulante findById(@PathVariable Long id){
        Postulante postulante = postulanteService.findByID(id);


        System.out.println(postulante.getCiudad().id_ciudad);
        System.out.println(postulante.getCorreo());

        return postulante;
    }

    @PutMapping("/postulante/{id}")
    public ResponseEntity<String> updatePostulante(@PathVariable Long id, @RequestBody PostulanteDto postulanteDto) {
        try {
            Postulante updatedPostulante = postulanteService.updatePostulante(id, postulanteDto);
            return ResponseEntity.status(HttpStatus.OK).body("Postulante actualizado correctamente");
        } catch (Error ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el postulante: " + ex.getMessage());
        }
    }

}






