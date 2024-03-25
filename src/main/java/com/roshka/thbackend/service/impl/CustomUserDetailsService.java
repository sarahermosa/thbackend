//package com.roshka.thbackend.service.impl;
//
//import com.roshka.thbackend.model.dao.UsuarioDao;
//import com.roshka.thbackend.model.entity.Usuario;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UsuarioDao usuarioDao;
//
//    public CustomUserDetailsService(UsuarioDao usuarioDao) {
//        this.usuarioDao = usuarioDao;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Usuario usuario = usuarioDao.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + email));
//
//
//        //ver despues
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        usuario.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getDescripcion())));
//
//        return new org.springframework.security.core.userdetails.User(usuario.getEmail(),
//                usuario.getPassword(),
//                authorities);
//    }
//}
//
