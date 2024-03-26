package com.roshka.thbackend.controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roshka.thbackend.model.entity.ERole;
import com.roshka.thbackend.model.entity.Rol;
import com.roshka.thbackend.model.entity.Usuario;
import com.roshka.thbackend.model.dto.LoginDto;
import com.roshka.thbackend.model.dto.SignUpDto;
import com.roshka.thbackend.model.payload.JwtResponse;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.model.dao.UsuarioDao;
import com.roshka.thbackend.model.dao.RolDao;
import com.roshka.thbackend.utils.JwtUtils;
import com.roshka.thbackend.service.impl.UserDetailsImpl;

@CrossOrigin
@RestController
@RequestMapping("/thbackend/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioDao userRepository;

    @Autowired
    RolDao roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getIdUsuario(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Error: Email is already token!")
                            .object(null)
                            .build()
                    , HttpStatus.BAD_REQUEST);
        }

        // Create new user's account
        Usuario user = new Usuario(signUpRequest.getNombre(), signUpRequest.getApellido(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        //insertar antes en la tabla de rol los datos (ROLE_ADMIN, ROLE_USER)

        Set<String> strRoles = signUpRequest.getRole();
        Set<Rol> roles = new HashSet<>();

        if (strRoles == null) {
            Rol userRole = roleRepository.findByDescripcion(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Rol adminRole = roleRepository.findByDescripcion(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Rol userRole = roleRepository.findByDescripcion(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        //RESPONDE CON EL CODIGO 201 Created

        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("User registered successfully!")
                .build()
                , HttpStatus.CREATED);
    }
}