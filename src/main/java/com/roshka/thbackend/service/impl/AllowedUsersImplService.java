package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.AllowedUsersDao;
import com.roshka.thbackend.model.dto.AllowedUsersDto;
import com.roshka.thbackend.model.entity.AllowedUsers;
import com.roshka.thbackend.service.AllowedUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AllowedUsersImplService implements AllowedUsersService {

    @Autowired
    private AllowedUsersDao allowedUsersDao;

    @Override
    public List<AllowedUsers> listAll() {
        return (List) allowedUsersDao.findAll();
    }

    @Transactional
    @Override
    public AllowedUsers save(AllowedUsersDto allowedUsersDto) {
        AllowedUsers allowedUser = AllowedUsers.builder()
                .id_user(allowedUsersDto.getId_user())
                .email(allowedUsersDto.getEmail())
                .build();
        return allowedUsersDao.save(allowedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public AllowedUsers findById(Integer id) {
        return allowedUsersDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(AllowedUsers usuario) {
        allowedUsersDao.delete(usuario);
    }

    @Override
    public boolean existsById(Integer id) {
        return allowedUsersDao.existsById(id);
    }

}
