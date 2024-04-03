package com.roshka.thbackend.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.roshka.thbackend.model.dao.AllowedUsersDao;
import com.roshka.thbackend.model.dto.*;
import com.roshka.thbackend.service.UsuarioService;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.roshka.thbackend.model.entity.ERole;
import com.roshka.thbackend.model.entity.Rol;
import com.roshka.thbackend.model.entity.Usuario;
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
    AllowedUsersDao allowedUsersRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsuarioService service;

    @Autowired //INYECCION DE DEPENDENCIAS PARA EL EMAIL
    private JavaMailSender javaMailSender;

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


        try {
            if(allowedUsersRepository.existsByEmail(signUpRequest.getEmail())){
                if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                    return new ResponseEntity<>(
                            MensajeResponse.builder()
                                    .mensaje("Error: El email ya esta registrado!")
                                    .object(null)
                                    .build()
                            , HttpStatus.BAD_REQUEST);
                }

                // Create new user's account
                Usuario user = new Usuario(signUpRequest.getNombre(), signUpRequest.getApellido(), signUpRequest.getEmail(),
                        encoder.encode(signUpRequest.getPassword()));

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
                        .object(user.getEmail())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Error: Usuario no permitido")
                                .object(null)
                                .build()
                        , HttpStatus.UNAUTHORIZED);
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

    @PostMapping("/forgot-password")
    public ResponseEntity<?>  forgotPass(@RequestBody ForgotPasswordDto request){

        try {
            if (userRepository.existsByEmail(request.getEmail())) {
                Usuario user = service.forgotPass(request.getEmail());

                SimpleMailMessage email = new SimpleMailMessage();
                email.setTo(request.getEmail());
                email.setFrom("bootcampjava341@gmail.com");
                email.setSubject("Recuperar Contraseña");
                email.setText("Hola, " + user.getNombre() +
                        "\n\n" +
                        "¡Hubo una solicitud para cambiar su contraseña!" +
                        "\n" +
                        "Si no realizó esta solicitud, ignore este correo electrónico." +
                        "\n" +
                        "De lo contrario, haga clic en este enlace para cambiar su contraseña:\n\nhttp://localhost:5173/confirm-reset?token="+user.getToken());
                javaMailSender.send(email);


                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Token generado")
                        .object(user.getTokenCreationDate())
                        .build()
                        , HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El email no existe")
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

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPass(@RequestBody ResetPasswordDto request){

        Optional<Usuario> userOptional = userRepository.findByToken(request.getToken());

        try {
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Invalid token")
                        .object(null)
                        .build()
                        , HttpStatus.NOT_FOUND);
            } else if (service.isTokenExpired(userOptional.get().getTokenCreationDate())){
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Token expired")
                                .object(null)
                                .build()
                        , HttpStatus.UNAUTHORIZED);
            } else {
                String response = service.resetPass(userOptional, encoder.encode(request.getPassword()));

                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje(response)
                        .object(userOptional.get().getEmail())
                        .build()
                        , HttpStatus.OK);
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

    @Transactional
    @PostConstruct
    public void init()  {
        // Verifica si la tabla de roles está vacía
        if (roleRepository.count() == 0) {
            Rol adminRole = Rol.builder().descripcion(ERole.ROLE_ADMIN).build();
            Rol userRole = Rol.builder().descripcion(ERole.ROLE_USER).build();

            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            // Hacer flush para sincronizar con la base de datos
            roleRepository.flush();

            System.out.println("Se han insertado los roles: "+ ERole.ROLE_USER+", " + ERole.ROLE_ADMIN);

        } else {
            System.out.println("La tabla de roles ya contiene registros");
        }

        //Crear usuario admin

        Set<Rol> rol = new HashSet<>();

        if(!userRepository.existsByEmail("test@roshka.com")) {
            Usuario usuarioAdmin = new Usuario("admin", "admin", "test@roshka.com",
                    encoder.encode("test"));

            Rol adminRole = roleRepository.findByDescripcion(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            rol.add(adminRole);

            usuarioAdmin.setRoles(rol);
            userRepository.save(usuarioAdmin);
            userRepository.flush();

            System.out.println("=======ADMIN USER=======");
            System.out.println("email: " + usuarioAdmin.getEmail());
            System.out.println("password: test");
        } else {
            System.out.println("Usuario TEST ya existe");
        }
    }
}