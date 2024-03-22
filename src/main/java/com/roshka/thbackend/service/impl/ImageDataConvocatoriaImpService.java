package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.entity.ImageDataConvocatoria;
import com.roshka.thbackend.service.IImageDataConvocatoriaService;
import com.roshka.thbackend.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.roshka.thbackend.model.dao.imageDataConvocatoriaDao;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageDataConvocatoriaImpService implements IImageDataConvocatoriaService {

    @Autowired
    private imageDataConvocatoriaDao imageDataConvocatoriaDao;

    @Override
    public String saveImage(MultipartFile multipartFile) throws IOException {

        ImageDataConvocatoria imageDataConvocatoria = imageDataConvocatoriaDao
                .save(ImageDataConvocatoria.builder()
                        .name(multipartFile.getOriginalFilename())
                        .type(multipartFile.getContentType())
                        .imageData(ImageUtils.compressImage(multipartFile.getBytes()))
                        .build());

        return null;
    }

    @Override
    public ImageDataConvocatoria findByImageById(Long id) {
        return null;
    }

    @Override
    public void deleteImage(Long id) {

    }
}
