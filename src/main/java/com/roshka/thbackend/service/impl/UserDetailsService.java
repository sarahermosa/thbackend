package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.BusinessException;
import com.roshka.thbackend.model.dao.UserTokenDao;
import com.roshka.thbackend.model.entity.Rol;
import com.roshka.thbackend.model.entity.Usuario;
import com.roshka.thbackend.model.entity.UserToken;
import com.roshka.thbackend.model.dao.UsuarioDao;
import com.roshka.thbackend.model.dto.UserDetailsDto;
import com.roshka.thbackend.model.dao.RolDao;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@Component
@AllArgsConstructor
public class UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UsuarioDao userRepository;
    @Autowired
    private UserTokenDao userTokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RolDao rolesDao;

    public String login(final Usuario user) {
        final Usuario userDb = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new BusinessException("This user is not registered"));

        if (!bCryptPasswordEncoder.matches(user.getPassword(), userDb.getPassword())) {
            throw new BusinessException("This user is not registered");
        }

        final Optional<UserToken> userToken = userTokenRepository.findTopByUser_idUsuarioOrderByIdDesc(userDb.getIdUsuario());

        if (userToken.isEmpty()) {
            return newToken(userDb).getUuid();
        }

        final UserToken topToken = userToken.get();

        if (topToken.getActive()) {
            throw new BusinessException("User already logged in");
        }

        return newToken(userDb).getUuid();
    }

    private UserToken newToken(final Usuario user) {
        UserToken newToken = new UserToken();
        newToken.setUser(user);
        newToken.setActive(Boolean.TRUE);
        newToken.setUuid(UUID.randomUUID().toString());
        return userTokenRepository.save(newToken);
    }

    private String encodePassword(final String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public Integer register(final Usuario user) {
        final Optional<Usuario> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new BusinessException("User already exists");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setNombre(user.getNombre());
        newUsuario.setApellido(user.getApellido());
        newUsuario.setEmail(user.getEmail());
        newUsuario.setPassword(encodePassword(user.getPassword()));


        //INSERT INTO public.rol (descripcion) VALUES ('usuario'); insertar antes usar el servicio
        Rol roles = rolesDao.findByDescripcion("usuario");
        newUsuario.setRoles(Collections.singleton(roles));

        return userRepository.save(newUsuario).getIdUsuario();
    }

    public void logout(final String token) {
        Optional<UserToken> userToken = userTokenRepository.findByUuid(token);

        if (userToken.isEmpty() || !userToken.get().getActive()) {
            throw new BusinessException("Invalid token to logout");
        }
        UserToken userTokenToInvalidate = userToken.get();

        log.info("logout user {}", userTokenToInvalidate.getUser().getIdUsuario());

        userTokenToInvalidate.setActive(false);
        userTokenRepository.save(userTokenToInvalidate);
    }

    public UserDetailsDto getUsuario(final Integer id) {
        final Usuario user = userRepository.findById(id).orElseThrow(() -> new BusinessException("No such user"));
        return new UserDetailsDto(user.getIdUsuario(), user.getEmail(), user.getNombre(), user.getApellido(), user.getRoles());
    }

    public UserDetails getUsuarioByToken(final String token) {
        Usuario userDb = userTokenRepository.findByUuid(token)
                .filter(userToken -> userToken.getActive())
                .map(UserToken::getUser).orElseThrow(() -> new BusinessException("Invalid token"));

        return new org.springframework.security.core.userdetails.User(userDb.getEmail(), userDb.getPassword(),
                true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
    }
}
