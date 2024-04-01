package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.dto.AllowedUsersDto;
import com.roshka.thbackend.model.entity.AllowedUsers;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.AllowedUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/thbackend/v1")
public class AllowedUsersController {

    @Autowired
    private AllowedUsersService allowedUsersService;

    //Listar usuarios permitidos
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allowedUsers")
    public ResponseEntity<?> showAll() {
        List<AllowedUsers> getList = allowedUsersService.listAll();
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

    //Crear usuario permitidos
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/allowedUsers")
    public ResponseEntity<?> create(@RequestBody AllowedUsersDto allowedUsersDto) {
        AllowedUsers usuarioSave = null;
        try {
            usuarioSave = allowedUsersService.save(allowedUsersDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(AllowedUsersDto.builder()
                            .id_user(usuarioSave.getId_user())
                            .email(usuarioSave.getEmail())
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

    //editar usuario permitido
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("allowedUsers/{id}")
    public ResponseEntity<?> update(@RequestBody AllowedUsersDto allowedUsersDto, @PathVariable Integer id) {
        AllowedUsers usuarioUpdate = null;
        try {
            if (allowedUsersService.existsById(id)) {
                allowedUsersDto.setId_user(id);
                usuarioUpdate = allowedUsersService.save(allowedUsersDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Guardado correctamente")
                        .object(AllowedUsersDto.builder()
                                .id_user(usuarioUpdate.getId_user())
                                .email(usuarioUpdate.getEmail())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El registro que intenta actualizar no se encuentra en la base de datos.")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    //eliminar usuario permitido
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("allowedUsers/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            AllowedUsers usuarioDelete = allowedUsersService.findById(id);
            allowedUsersService.delete(usuarioDelete);
            return new ResponseEntity<>(usuarioDelete, HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    //obtener usuario por ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("allowedUsers/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        AllowedUsers usuario = allowedUsersService.findById(id);

        if (usuario == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(AllowedUsersDto.builder()
                                .id_user(usuario.getId_user())
                                .email(usuario.getEmail())
                                .build())
                        .build()
                , HttpStatus.OK);
    }
}
