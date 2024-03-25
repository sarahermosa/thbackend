package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.dto.FileDto;
import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.Ciudad;
import com.roshka.thbackend.model.entity.Estado;
import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.entity.Postulante;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.EstadoService;
import com.roshka.thbackend.service.ICiudadService;
import com.roshka.thbackend.service.IFileService;
import com.roshka.thbackend.service.IPostulanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired //INYECCION DE DEPENDENCIAS PARA EL EMAIL
    private JavaMailSender javaMailSender;

    @PostMapping("postulante")
   public ResponseEntity<?> createPostulante(@RequestBody PostulanteDto postulante){
        Optional<Ciudad> ciudad = ciudadService.findById(postulante.getId_ciudad());
        Optional<Estado> estado = Optional.ofNullable(estadoService.findById(postulante.getId_estado()));

        ciudad.ifPresent(postulante::setCiudad);
        estado.ifPresent(postulante::setEstado);

        //CONFIGURACION PARA EL ENVIO DEL CORREO
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo("ferledesma352@gmail.com");
        email.setFrom("bootcampjava341@gmail.com");
        email.setSubject("Incripcion Convocatoria");
        email.setText("Hola!!" + postulante.getNombre() + "Gracias por inscribirte a la convocatoria\n\nNO RESPONDER ESTE MENSAJE");

        javaMailSender.send(email);
        //FIN DE CONFIGURACION PARA ENVIO DE CORREO

        try {
            postulanteService.savePostulante(postulante);
            return ResponseEntity.ok().body("Guardado correctamente");
        }catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la entidad: " + e.getMessage());
            }


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






