package com.roshka.thbackend.service;


import com.roshka.thbackend.model.dto.FileDto;
import com.roshka.thbackend.model.entity.File;

import java.util.List;

public interface IFileService {

    List<File> listAll();

    File save(FileDto file);

    File findById(Long id);

    void delete(File file);

    boolean existsById(File file);
}
