package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.UserToken;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserTokenDao extends JpaRepository<UserToken, Integer> {

    Optional<UserToken> findTopByUser_idUsuarioOrderByIdDesc(Integer idUsuario);

    Optional<UserToken> findByUuid(String token);

}
