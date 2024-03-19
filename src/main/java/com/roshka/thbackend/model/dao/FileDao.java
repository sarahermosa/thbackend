package com.roshka.thbackend.model.dao;

import com.roshka.thbackend.model.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileDao extends CrudRepository <File, Long> {
}
