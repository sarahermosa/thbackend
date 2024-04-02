package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.AllowedUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AllowedUsersDao extends JpaRepository<AllowedUsers, Integer> {
    Optional<AllowedUsersDao> findByEmail(String email);

    boolean existsByEmail(String email);

}
