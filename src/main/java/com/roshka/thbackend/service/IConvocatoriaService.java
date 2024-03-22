package com.roshka.thbackend.service;

import com.roshka.thbackend.model.dto.ConvocatoriaDto;
import com.roshka.thbackend.model.entity.Convocatoria;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

public interface IConvocatoriaService {
    List<Convocatoria> listAll() throws DataFormatException, IOException;
    Convocatoria save(ConvocatoriaDto convocatoriaDto) throws IOException;
    Convocatoria findById(Long id);
    void delete(Long id);

}
