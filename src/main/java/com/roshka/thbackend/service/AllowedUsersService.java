package com.roshka.thbackend.service;


import com.roshka.thbackend.model.dto.AllowedUsersDto;
import com.roshka.thbackend.model.dto.UsuarioDto;
import com.roshka.thbackend.model.entity.AllowedUsers;
import com.roshka.thbackend.model.entity.Usuario;

import java.util.List;

public interface AllowedUsersService {
    List<AllowedUsers> listAll();

    AllowedUsers save(AllowedUsersDto allowedUser);

    AllowedUsers findById(Integer id);

    void delete(AllowedUsers usuario);

    boolean existsById(Integer id);

}
