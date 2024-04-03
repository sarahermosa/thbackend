package com.roshka.thbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshka.thbackend.model.dto.*;
import com.roshka.thbackend.model.entity.*;
import com.roshka.thbackend.service.IConvocatoriaService;
import com.roshka.thbackend.service.IPostulanteService;
import com.roshka.thbackend.model.dto.FileDto;
import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.*;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.*;
import com.roshka.thbackend.service.impl.PostulanteImpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/thbackend/v1")
public class PostulanteController {

    @Autowired
    private IPostulanteService postulanteService;

    @Autowired
    private IConvocatoriaService convocatoriaService;

    @Autowired //INYECCION DE DEPENDENCIAS PARA EL EMAIL
    private JavaMailSender javaMailSender;

    @PostMapping("postulante")
   public ResponseEntity<?> createPostulante(@RequestParam("postulante_info") String postulante,
                                             @RequestParam("files") MultipartFile[] files,
                                             @RequestParam("experiencias") String experiencias,
                                             @RequestParam("estudios") String estudios,
                                             @RequestParam("tecnologias_id") String tecnologiasId,
                                             @RequestParam("referencias_personales") String referencias,
                                             @RequestParam("convocatoria_id") String convocatoriaId)
                                             throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        try{

            PostulanteDto dto = mapper.readValue(postulante, PostulanteDto.class);
            List<Experiencia> experienciasList = mapper.readValue(experiencias, mapper.getTypeFactory().constructCollectionType(List.class, Experiencia.class));
            List<MultipartFile> incomingFiles = Arrays.asList(files);
            List<Estudio> estudiosList = mapper.readValue(estudios, mapper.getTypeFactory().constructCollectionType(List.class, Estudio.class));
            List<Long> tecnologiasListId = mapper.readValue(tecnologiasId, mapper.getTypeFactory().constructCollectionType(List.class, Long.class));
            List<ReferenciaPersonal> referenciaPersonalList = mapper.readValue(referencias, mapper.getTypeFactory().constructCollectionType(List.class, ReferenciaPersonal.class));
            Convocatoria convocatoria = convocatoriaService.findById(Long.parseLong(convocatoriaId));

            dto.setConvocatoria(convocatoria);
            dto.setFilesMultipart(incomingFiles);
            dto.setExperiencias(experienciasList);
            dto.setEstudios(estudiosList);
            dto.setTecnologiasList(tecnologiasListId);
            dto.setReferencia_personal(referenciaPersonalList);


            System.out.println(dto);

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(dto.getCorreo());
            email.setFrom("bootcampjava341@gmail.com");
            email.setSubject("Incripcion Convocatoria");
            email.setText("Hola!! " + dto.getNombre() + " Gracias por inscribirte a la convocatoria\n\nNO RESPONDER ESTE MENSAJE");
            javaMailSender.send(email);


            postulanteService.savePostulante(dto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


        return ResponseEntity.ok().body("Guardado correctamente");

    }

//    @GetMapping("/postulantes?nombre={nombre}")
//    public List<Postulante> buscarPorNombre(@PathVariable String nombre) {
//        System.out.println(nombre);
//        return postulanteService.buscarPorNombre(nombre);
//    }

    @GetMapping("/postulantes")
    public List<Postulante> buscarPorNombre(@RequestParam(name = "nombre", required = false) String nombre,
                                            @RequestParam(name = "apellido", required = false) String apellido,
                                            @RequestParam(name = "estado", required = false) String estado) {
        if (nombre != null) {
            return postulanteService.buscarPorNombre(nombre);
        } else if (apellido != null) {
            return postulanteService.buscarPorApellido(apellido);
        } else if (estado != null){
            return postulanteService.buscarPorEstado(Long.parseLong(estado));
        }
        else {
            // Handle case when neither nombre nor apellido is provided
            throw new IllegalArgumentException("Debe proporcionar nombre, apellido o estado para la búsqueda.");
        }
    }

    //

//
//    @GetMapping("/documento/{numeroDocumento}")
//    public ResponseEntity<Postulante> buscarPorNumeroDocumento(@PathVariable String numeroDocumento) {
//        Postulante postulante = postulanteService.buscarPorNumeroDocumento(numeroDocumento);
//        if (postulante != null) {
//            return ResponseEntity.ok(postulante);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
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
    public ResponseEntity<String> updatePostulante(@PathVariable Long id,
                                                   @RequestParam("postulante_info") String postulante,
                                                   @RequestParam("files") MultipartFile[] files,
                                                   @RequestParam("experiencias") String experiencias,
                                                   @RequestParam("estudios") String estudios,
                                                   @RequestParam("tecnologias_id") String tecnologiasId,
                                                   @RequestParam("referencias_personales") String referencias) {
//        try {
//            Postulante updatedPostulante = postulanteService.updatePostulante(id,postulante);
//            return ResponseEntity.status(HttpStatus.OK).body("Postulante actualizado correctamente");
//        } catch (Error ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el postulante: " + ex.getMessage());
//        }

        System.out.println("update in process");
        System.out.println(tecnologiasId);
        ObjectMapper mapper = new ObjectMapper();
        try{

            PostulanteDto dto = mapper.readValue(postulante, PostulanteDto.class);
            List<Experiencia> experienciasList = mapper.readValue(experiencias, mapper.getTypeFactory().constructCollectionType(List.class, Experiencia.class));
            List<MultipartFile> incomingFiles = Arrays.asList(files);
            List<Estudio> estudiosList = mapper.readValue(estudios, mapper.getTypeFactory().constructCollectionType(List.class, Estudio.class));
            List<Long> tecnologiasListId = mapper.readValue(tecnologiasId, mapper.getTypeFactory().constructCollectionType(List.class, Long.class));
            List<ReferenciaPersonal> referenciaPersonalList = mapper.readValue(referencias, mapper.getTypeFactory().constructCollectionType(List.class, ReferenciaPersonal.class));


            dto.setFilesMultipart(incomingFiles);
            dto.setExperiencias(experienciasList);
            dto.setEstudios(estudiosList);
            dto.setTecnologiasList(tecnologiasListId);
            dto.setReferencia_personal(referenciaPersonalList);


            postulanteService.updatePostulante( id,dto);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


        return ResponseEntity.ok().body("Actualizado correctamente");

    }

    }






