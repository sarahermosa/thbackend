package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.File;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileDao extends CrudRepository <File, Long> {

}
