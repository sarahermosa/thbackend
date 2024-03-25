package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.dto.UsuarioDto;
import com.roshka.thbackend.model.entity.Usuario;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/thbackend/v1")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Listar usuarios
    @GetMapping("usuarios")
    public ResponseEntity<?> showAll() {
        List<Usuario> getList = usuarioService.listAll();
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

    //Crear usuario
    @PostMapping("usuarios")
    public ResponseEntity<?> create(@RequestBody UsuarioDto usuarioDto) {
        Usuario usuarioSave = null;
        try {
            usuarioSave = usuarioService.save(usuarioDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(UsuarioDto.builder()
                            .idUsuario(usuarioSave.getIdUsuario())
                            .email(usuarioSave.getEmail())
                            .nombre(usuarioSave.getNombre() )
                            .apellido(usuarioSave.getApellido())
                            .password(usuarioSave.getPassword())
                            .resetPassword(usuarioSave.isResetPassword())
                            .roles(usuarioSave.getRoles())
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

    //editar usuario
    @PutMapping("usuario/{id}")
    public ResponseEntity<?> update(@RequestBody UsuarioDto usuarioDto, @PathVariable Integer id) {
        Usuario usuarioUpdate = null;
        try {
            if (usuarioService.existsById(id)) {
                usuarioDto.setIdUsuario(id);
                usuarioUpdate = usuarioService.save(usuarioDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Guardado correctamente")
                        .object(UsuarioDto.builder()
                                .idUsuario(usuarioUpdate.getIdUsuario())
                                .email(usuarioUpdate.getEmail())
                                .nombre(usuarioUpdate.getNombre())
                                .apellido(usuarioUpdate.getApellido())
                                .password(usuarioUpdate.getPassword())
                                .resetPassword(usuarioUpdate.isResetPassword())
                                .roles(usuarioUpdate.getRoles())
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

    //eliminar usuario
    @DeleteMapping("usuario/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Usuario usuarioDelete = usuarioService.findById(id);
            usuarioService.delete(usuarioDelete);
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
    @GetMapping("usuario/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.findById(id);

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
                        .object(UsuarioDto.builder()
                                .idUsuario(usuario.getIdUsuario())
                                .email(usuario.getEmail())
                                .nombre(usuario.getNombre() )
                                .apellido(usuario.getApellido())
                                .password(usuario.getPassword())
                                .resetPassword(usuario.isResetPassword())
                                .roles(usuario.getRoles())
                                .build())
                        .build()
                , HttpStatus.OK);
    }
}
