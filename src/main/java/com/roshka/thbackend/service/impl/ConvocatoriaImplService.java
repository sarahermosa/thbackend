package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.ConvocatoriaDao;
import com.roshka.thbackend.model.dao.TecnologiaDao;
import com.roshka.thbackend.model.dto.ConvocatoriaDto;
import com.roshka.thbackend.model.dto.ConvocatoriaOutputDto;
import com.roshka.thbackend.model.entity.Convocatoria;
import com.roshka.thbackend.model.entity.File;
import com.roshka.thbackend.model.entity.Postulante;
import com.roshka.thbackend.model.entity.Tecnologia;
import com.roshka.thbackend.service.IConvocatoriaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ConvocatoriaImplService implements IConvocatoriaService {
    @Autowired
    private ConvocatoriaDao convocatoriaDao;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TecnologiaDao tecnologiaDao;

    @Autowired
    private HttpServletRequest request;

    @Override
    public List<ConvocatoriaOutputDto> listAll() throws DataFormatException, IOException {
        List<Convocatoria> convocatorias = (List) convocatoriaDao.findAll();
        List<ConvocatoriaOutputDto> convocatoriasOut = new ArrayList<ConvocatoriaOutputDto>();

        for (Convocatoria convocatoria : convocatorias) {
            ConvocatoriaOutputDto output = new ConvocatoriaOutputDto();
            output.setId_convocatoria(convocatoria.getId_convocatoria());
            output.setTitle(convocatoria.getTitle());
            output.setDescription(convocatoria.getDescription());
            output.setFecha_inicio(convocatoria.getFecha_inicio());
            output.setFecha_fin(convocatoria.getFecha_fin());
            output.setLink(convocatoria.getLink());
            output.setFile_path(convocatoria.getImageData());
            output.setTecnologias(convocatoria.getTecnologiasasignadas().stream().toList());

//            String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
//            String imageUrl = baseUrl + "/" + convocatoria.getImageData().replace("\\", "/"); // Replace backslashes with forward slashes

//            output.setFile_path(imageUrl);
            convocatoriasOut.add(output);
        }
        return convocatoriasOut;
    }


    @Override
    @Transactional
    public Convocatoria save(ConvocatoriaDto convocatoriaDto) throws IOException {

        System.out.println(convocatoriaDto);
        if (convocatoriaDto.getFile() != null && !convocatoriaDto.getFile().isEmpty()) {
            InputStream fileInputStream = convocatoriaDto.getFile().getInputStream();
            String fileExtension = convocatoriaDto.getFile().getOriginalFilename().substring(convocatoriaDto.getFile().getOriginalFilename().lastIndexOf('.'));

            Path directoriImagenes = Paths.get("images/" + DigestUtils.md5DigestAsHex(fileInputStream) + fileExtension);
            String rutaAbsoluta = directoriImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = convocatoriaDto.getFile().getBytes();
                String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                Path rutaCompleta = Paths.get(rutaAbsoluta);
                Files.write(rutaCompleta, bytesImg);


                Convocatoria convocatoria = Convocatoria.builder()
                        .title(convocatoriaDto.getTitle())
                        .description(convocatoriaDto.getDescription())
                        .fecha_inicio(convocatoriaDto.getFecha_inicio())
                        .fecha_fin(convocatoriaDto.getFecha_fin())
                        .link(convocatoriaDto.getLink())
                        .imageData(baseUrl + "/" + directoriImagenes.toString().replace("\\", "/"))
                        .tecnologiasasignadas(new HashSet<>())
                        .build();

                convocatoriaDao.save(convocatoria);



                for (Long tecnologiaId : convocatoriaDto.getTecnologias_ids()) {
                    assignTecnologiaToConvocatoria(convocatoria.getId_convocatoria(), tecnologiaId);
                }

                Convocatoria convocatoria_cambiar_link = convocatoriaDao.findById(convocatoria.getId_convocatoria()).get();
                System.out.print(convocatoria_cambiar_link);
                convocatoria_cambiar_link.setLink("/convocatoria/"+ convocatoria_cambiar_link.getId_convocatoria().toString());
                return convocatoriaDao.save(convocatoria_cambiar_link);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Convocatoria convocatoria = Convocatoria.builder()
                    .title(convocatoriaDto.getTitle())
                    .description(convocatoriaDto.getDescription())
                    .fecha_inicio(convocatoriaDto.getFecha_inicio())
                    .imageData(null)
                    .fecha_fin(convocatoriaDto.getFecha_fin())
                    .link(convocatoriaDto.getLink())
                    .tecnologiasasignadas(new HashSet<>())
                    .build();

            convocatoriaDao.save(convocatoria);

            for (Long tecnologiaId : convocatoriaDto.getTecnologias_ids()) {
                assignTecnologiaToConvocatoria(convocatoria.getId_convocatoria(), tecnologiaId);
            }

            Convocatoria convocatoria_cambiar_link = convocatoriaDao.findById(convocatoria.getId_convocatoria()).get();
            convocatoria_cambiar_link.setLink("/convocatoria/"+ convocatoria_cambiar_link.getId_convocatoria().toString());

            return convocatoriaDao.save(convocatoria_cambiar_link);
        }


    }


    @Override
    public Convocatoria findById(Long id) {
        return convocatoriaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        convocatoriaDao.deleteById(id);
    }


    //utils
    public static final int BITE_SIZE = 4 * 1024;

    public static byte[] compressImage(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[BITE_SIZE];

        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }

        outputStream.close();

        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[BITE_SIZE];

        while (!inflater.finished()) {
            int count = inflater.inflate(tmp);
            outputStream.write(tmp, 0, count);
        }

        outputStream.close();

        return outputStream.toByteArray();
    }

    @Override
    public Convocatoria assignTecnologiaToConvocatoria(Long convocatoriaId, Long tecnlogiaId) {

        Tecnologia tecnologia = tecnologiaDao.findById(tecnlogiaId).get();
        Convocatoria convocatoria = convocatoriaDao.findById(convocatoriaId).get();
        Set<Tecnologia> tecnologiaSet = convocatoria.getTecnologiasasignadas();
        tecnologiaSet.add(tecnologia);
        convocatoria.setTecnologiasasignadas(tecnologiaSet);
        return convocatoriaDao.save(convocatoria);
    }

    @Override
    public Convocatoria updateConvocatoria(Long id, ConvocatoriaDto convocatoriaDto) throws IOException {

        Optional<Convocatoria> optionalConvocatoria = convocatoriaDao.findById(id);
        if (optionalConvocatoria.isPresent()) {
            Convocatoria convocatoria = optionalConvocatoria.get();
            convocatoriaDto.setId_convocatoria(id);
            modelMapper.map(convocatoriaDto, convocatoria);
            convocatoria.setImageData(null);
            convocatoria.setTecnologiasasignadas(new HashSet<>());
            convocatoriaDao.save(convocatoria);

            for (Long tecnologiaId : convocatoriaDto.getTecnologias_ids()) {
                assignTecnologiaToConvocatoria(convocatoria.getId_convocatoria(), tecnologiaId);
            }

            if (convocatoriaDto.getFile() != null && !convocatoriaDto.getFile().isEmpty()) {
                InputStream fileInputStream = convocatoriaDto.getFile().getInputStream();
                String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                String fileExtension = convocatoriaDto.getFile().getOriginalFilename().substring(convocatoriaDto.getFile().getOriginalFilename().lastIndexOf('.'));
                Path directoriImagenes = Paths.get("images/" + DigestUtils.md5DigestAsHex(fileInputStream) + fileExtension);
                convocatoria.setImageData(baseUrl + "/" + directoriImagenes.toString().replace("\\", "/"));

            } else {
                convocatoria.setImageData(null);
                return convocatoriaDao.save(convocatoria);
            }

            convocatoriaDao.save(convocatoria);
            return convocatoria;
        }
        else {
            throw new RuntimeException("Error al actualizar la convocatoria. No se encontró el postulante con ID: " + id);
        }

    }
}
