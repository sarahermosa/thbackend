package com.roshka.thbackend.controller;


import com.roshka.thbackend.model.dto.FileDto;
import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.payload.MensajeResponse;
import com.roshka.thbackend.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thbackend/v1")
public class FileController {
    @Autowired
    private IFileService fileService;


    @PostMapping("file")
    public ResponseEntity<?> create(@RequestBody FileDto fileDto) {
        File fileSave = null;
        try {
            fileSave = fileService.save(fileDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("File Guardado correctamente")
                    .object(fileDto.builder()
                            .id_files(fileSave.getId_files())
                            .file_name(fileSave.getFile_name())
                            .file_type(fileSave.getFile_type())
                            .data(fileSave.getData())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
