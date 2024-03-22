package com.roshka.thbackend.service;

import com.roshka.thbackend.model.entity.ImageDataConvocatoria;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageDataConvocatoriaService {

    String saveImage(MultipartFile multipartFile) throws IOException;
    ImageDataConvocatoria findByImageById(Long id);
    void deleteImage(Long id);

}
