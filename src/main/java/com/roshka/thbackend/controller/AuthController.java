package com.roshka.thbackend.controller;

import com.roshka.thbackend.model.entity.Usuario;
import com.roshka.thbackend.model.entity.Rol;
import com.roshka.thbackend.model.dto.LoginDto;
import com.roshka.thbackend.model.dto.SignUpDto;
import com.roshka.thbackend.model.dao.RolDao;
import com.roshka.thbackend.model.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/thbackend/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private RolDao rolesDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for email exists in DB
        if(usuarioDao.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        Usuario usuario = new Usuario();
        usuario.setNombre(signUpDto.getNombre());
        usuario.setApellido(signUpDto.getApellido());
        usuario.setEmail(signUpDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        //INSERT INTO public.rol (descripcion) VALUES ('usuario'); insertar antes usar el servicio
        Rol roles = rolesDao.findByDescripcion("usuario");
        usuario.setRoles(Collections.singleton(roles));

        usuarioDao.save(usuario);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}
