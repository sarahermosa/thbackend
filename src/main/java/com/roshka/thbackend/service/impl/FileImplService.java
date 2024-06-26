package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.FileDao;
import com.roshka.thbackend.model.dao.PostulanteDao;
import com.roshka.thbackend.model.dto.FileDto;
import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.entity.Postulante;
import com.roshka.thbackend.service.IFileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Service
public class FileImplService implements IFileService {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private PostulanteDao postulanteDao;


    @Override
    public List<File> listAll() {
        return (List) fileDao.findAll();
    }

    @Transactional
    @Override
    public File save(FileDto fileDto) {
      File file = File.builder()
              .id_files(fileDto.getId_files())
              .file_type(fileDto.getFile_type())
//              .data(fileDto.getData())
              .build();
      return fileDao.save(file);
    }

    @Override
    public File findById(Long id) {
        return fileDao.findById(id).orElse(null);
    }

    @Override
    public void delete(File file) {
        List<Postulante> postulantes = postulanteDao.findByFilesContaining(file);
        for (Postulante postulante : postulantes) {
            postulante.getFiles().remove(file);
            postulanteDao.save(postulante); // Update the Postulante entity
        }
        fileDao.delete(file); // Delete the file
    }





    @Override
    public boolean existsById(Long id) {
        return fileDao.existsById(id);
    }





}
