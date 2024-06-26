package com.roshka.thbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshka.thbackend.model.dao.PostulanteDao;
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
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@RestController
@CrossOrigin
@RequestMapping("/thbackend/v1")
public class PostulanteController {

    @Autowired
    private IPostulanteService postulanteService;

    @Autowired
    private IConvocatoriaService convocatoriaService;

    @Autowired
    private IFileService FileService;

    @Autowired //INYECCION DE DEPENDENCIAS PARA EL EMAIL
    private JavaMailSender javaMailSender;

    @PostMapping("postulante")
   public ResponseEntity<?> createPostulante(@RequestParam("postulante_info") String postulante,
                                             @RequestParam(name = "files", required = false) MultipartFile[] files,
                                             @RequestParam("experiencias" ) String experiencias,
                                             @RequestParam("estudios") String estudios,
                                             @RequestParam("tecnologias_id") String tecnologiasId,
                                             @RequestParam("referencias_personales") String referencias,
                                             @RequestParam("convocatoria_id") String convocatoriaId)
                                             throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        try{

            PostulanteDto dto = mapper.readValue(postulante, PostulanteDto.class);
            List<Experiencia> experienciasList = null;
            if(!experiencias.isEmpty()) {
                experienciasList = mapper.readValue(experiencias, mapper.getTypeFactory().constructCollectionType(List.class, Experiencia.class));
            }
            List<MultipartFile> incomingFiles = null;
            if(files != null) {
                incomingFiles = Arrays.asList(files);
            }
            List<Estudio> estudiosList = null;
            if(!estudios.isEmpty()) {
                estudiosList = mapper.readValue(estudios, mapper.getTypeFactory().constructCollectionType(List.class, Estudio.class));
            }
            List<Long> tecnologiasListId = null;
            if(!tecnologiasId.equals("[]")){
                tecnologiasListId = mapper.readValue(tecnologiasId, mapper.getTypeFactory().constructCollectionType(List.class, Long.class));
            }
            List<ReferenciaPersonal> referenciaPersonalList = null;
            if(!referencias.isEmpty()){
                referenciaPersonalList = mapper.readValue(referencias, mapper.getTypeFactory().constructCollectionType(List.class, ReferenciaPersonal.class));
            }
            Convocatoria convocatoria = convocatoriaService.findById(Long.parseLong(convocatoriaId));

            dto.setConvocatoria(convocatoria);
            if(incomingFiles != null){
                dto.setFilesMultipart(incomingFiles);
            }
            if(experienciasList != null){
                dto.setExperiencias(experienciasList);
            }
            if(estudiosList != null){
                dto.setEstudios(estudiosList);
            }
            if(tecnologiasListId != null){
                dto.setTecnologiasList(tecnologiasListId);
            }
            dto.setReferencia_personal(referenciaPersonalList);

            System.out.println(dto);


//            SimpleMailMessage email = new SimpleMailMessage();
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper email = new MimeMessageHelper(message, true);

            email.setTo(dto.getCorreo());
            email.setFrom("bootcampjava341@gmail.com");
            email.setSubject("Inscripción Convocatoria");

            String htmlFilePath = "src/main/resources/templates/email-template.html";
            String logoUrl = "https://i.postimg.cc/rpYs8GHX/roshka-logo-white.png"; // Replace with the actual URL of your image

            String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));
            htmlContent = htmlContent.replace("{nombre}", dto.getNombre());
            htmlContent = htmlContent.replace("{nombreconvocatoria}", dto.getConvocatoria().getTitle());
            htmlContent = htmlContent.replace("{logo}", logoUrl);

            email.setText(htmlContent, true);
            javaMailSender.send(message);



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
                                            @RequestParam(name = "estado", required = false) String estado,
                                            @RequestParam(name = "nro_documento", required = false) String nro_documento,
                                            @RequestParam(name = "id_tecnologia", required = false) String id_tecnologia){

        if (nombre != null && nombre.isEmpty()) {
            nombre = null;
        }
        if (apellido != null && apellido.isEmpty()) {
            apellido = null;
        }
        if (estado != null && estado.isEmpty()) {
            estado = null;
        }
        if (nro_documento != null && nro_documento.isEmpty()) {
            nro_documento = null;
        }
        if (id_tecnologia != null && id_tecnologia.isEmpty()) {
            id_tecnologia = null;
        }

        // Pass null values to the filtro method
        List<Postulante> postulantes = postulanteService.filtro(nombre, apellido, estado != null ? Long.parseLong(estado) : null, nro_documento, id_tecnologia != null ? Long.parseLong(id_tecnologia) : null);
        return postulantes;
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

        return postulante;
    }

    @PutMapping("/postulante/{id}")
    public ResponseEntity<String> updatePostulante(@PathVariable Long id,
                                                   @RequestParam("postulante_info") String postulante,
                                                   @RequestParam(value = "files", required = false) MultipartFile[] files,
                                                   @RequestParam("experiencias") String experiencias,
                                                   @RequestParam("estudios") String estudios,
                                                   @RequestParam("tecnologias_id") String tecnologiasId,
                                                   @RequestParam("referencias_personales") String referencias,
                                                   @RequestParam("convocatoria_id") String convocatoriaId){
//


        System.out.println("update in process");
        ObjectMapper mapper = new ObjectMapper();
        try{

            PostulanteDto dto = mapper.readValue(postulante, PostulanteDto.class);
            List<Experiencia> experienciasList = null;
            if(!experiencias.isEmpty()) {
                experienciasList = mapper.readValue(experiencias, mapper.getTypeFactory().constructCollectionType(List.class, Experiencia.class));
            }
            List<MultipartFile> incomingFiles = null;
            if(files != null) {
                incomingFiles = Arrays.asList(files);
            }
            List<Estudio> estudiosList = null;
            if(!estudios.isEmpty()) {
                estudiosList = mapper.readValue(estudios, mapper.getTypeFactory().constructCollectionType(List.class, Estudio.class));
            }
            List<Long> tecnologiasListId = null;
            if(!tecnologiasId.equals("[]")){
                tecnologiasListId = mapper.readValue(tecnologiasId, mapper.getTypeFactory().constructCollectionType(List.class, Long.class));
            }
            List<ReferenciaPersonal> referenciaPersonalList = null;
            if(!referencias.isEmpty()){
                referenciaPersonalList = mapper.readValue(referencias, mapper.getTypeFactory().constructCollectionType(List.class, ReferenciaPersonal.class));
            }

            Convocatoria convocatoria = convocatoriaService.findById(Long.parseLong(convocatoriaId));

            dto.setConvocatoria(convocatoria);
            if(incomingFiles != null){
                dto.setFilesMultipart(incomingFiles);
            }
            if(experienciasList != null){
                dto.setExperiencias(experienciasList);
            }
            if(estudiosList != null){
                dto.setEstudios(estudiosList);
            }
            if(tecnologiasListId != null){
                dto.setTecnologiasList(tecnologiasListId);
            }
            dto.setReferencia_personal(referenciaPersonalList);

            System.out.println(dto);



            postulanteService.updatePostulante( id,dto);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


        return ResponseEntity.ok().body("Actualizado correctamente");

    }




    @GetMapping("postulante_tecnologia/{id}")
    public ResponseEntity<?> listConvocatoriaTecnologia(@PathVariable Long id) {
        try {
            List<Postulante> output = new ArrayList<>();
            List<Postulante> listaDePostulante =postulanteService.listAll();

            listaDePostulante.stream()
                    .filter(postulante -> postulante.getTecnologiasasignadas().stream()
                            .anyMatch(tecnologia-> tecnologia.getId_tecnologia() == id))
                    .forEach(output :: add);



            return ResponseEntity.ok().body(output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }


    @DeleteMapping("/eliminarfile/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        try {
            File file = FileService.findById(id);
            FileService.delete(file);
            return ResponseEntity.ok().body("Archivo eliminado correctamente");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }


    @DeleteMapping("postulante/{id}")
    public ResponseEntity<?> deletePostulante(@PathVariable Long id) {
        try {
            postulanteService.deletePostulante(id);
            return ResponseEntity.ok().body("Postulante eliminado correctamente");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
}


