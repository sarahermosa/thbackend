package com.roshka.thbackend.service.impl;

import com.roshka.thbackend.model.dao.CiudadDao;
import com.roshka.thbackend.model.dao.EstadoDao;
import com.roshka.thbackend.model.dao.PostulanteDao;
import com.roshka.thbackend.model.dao.TecnologiaDao;
import com.roshka.thbackend.model.dto.PostulanteDto;
import com.roshka.thbackend.model.entity.*;
import com.roshka.thbackend.service.IPostulanteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PostulanteImpService implements IPostulanteService {

    @Autowired
    private PostulanteDao postulanteDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TecnologiaDao tecnologiaDao;

    @Autowired
    private EstadoDao estadoDao;

    @Autowired
    private CiudadDao ciudadDao;
    @Autowired
    private HttpServletRequest request;


    @Override
    public List<Postulante> listAll() {
        return  (List) postulanteDao.findAll();
    }

    @Override
    @Transactional
    public Postulante savePostulante(PostulanteDto PostulanteDto) throws IOException {
        System.out.println(PostulanteDto);
        System.out.println(PostulanteDto.getReferencia_personal());
        Postulante postulante = modelMapper.map(PostulanteDto, Postulante.class);
        postulante.setTecnologiasasignadas(new HashSet<>());
        postulanteDao.save(postulante);

        for (Long tecnologiaId : PostulanteDto.getTecnologiasList()) {
            assignTecnologiaToPostulante(postulante.getId_postulante(), tecnologiaId);
        }

        List<File> files = new ArrayList<>();
        for ( MultipartFile file : PostulanteDto.getFilesMultipart()) {
            InputStream fileInputStream = file.getInputStream();
            String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
            Path directoriImagenes =  Paths.get("cv/" + DigestUtils.md5DigestAsHex(fileInputStream)+fileExtension);
            String rutaAbsoluta = directoriImagenes.toFile().getAbsolutePath();
            try{
                byte[] bytesImg=file.getBytes();
                Path rutaCompleta=Paths.get(rutaAbsoluta);
                Files.write(rutaCompleta, bytesImg);
            }catch(IOException e){
                System.out.println("Error al subir el archivo");
            }
            File f = new File();
            String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
            String fileUrl = baseUrl+"/"+directoriImagenes.toString().replace("\\", "/");
            f.setLinkToFile(fileUrl);
            f.setFile_type(fileExtension);
            files.add(f);
        }
        postulante.setFiles(files);

        assignCityToPostulante(postulante.getId_postulante(), PostulanteDto.getId_ciudad());
        assignEstadoToPostulante(postulante.getId_postulante(), PostulanteDto.getId_estado());



        postulanteDao.save(postulante);
        System.out.println(postulante);
        return postulante;

    }

    @Override
    public Postulante findByID(Long id_postulante) {
        return postulanteDao.findById(id_postulante).orElse(null);
    }

    @Override
    public void deletePostulante(Long id) {
        postulanteDao.deleteById(id);
    }

    @Override
    public Postulante updatePostulante(Long id, PostulanteDto postulanteDto) {
        Optional<Postulante> optionalPostulante = postulanteDao.findById(id);

        if (optionalPostulante.isPresent()) {
            Postulante postulante = optionalPostulante.get();

            postulante.setNombre(postulanteDto.getNombre());
            postulante.setApellido(postulanteDto.getApellido());
            postulante.setComentario_rrhh(postulanteDto.getComentario_rrhh());
            postulante.setCorreo(postulanteDto.getCorreo());
            postulante.setDireccion(postulanteDto.getDireccion());
            postulante.setNro_telefono(postulanteDto.getNro_telefono());
            postulante.setNacionalidad(postulanteDto.getNacionalidad());
            postulante.setEstado_civil(postulanteDto.getEstado_civil());
            postulante.setFecha_nacimiento(postulanteDto.getFecha_nacimiento());
            postulante.setFecha_actualizacion(postulanteDto.getFecha_actualizacion());
            postulante.setFecha_creacion(postulanteDto.getFecha_creacion());
            postulante.setFecha_contratado(postulanteDto.getFecha_contratado());
            postulante.setNivel_ingles(postulanteDto.getNivel_ingles());
            return postulanteDao.save(postulante);
        } else {
            throw new RuntimeException("Error al actualizar el postulante. No se encontró el postulante con ID: " + id);
        }
    }

    @Override
    public Postulante assignTecnologiaToPostulante(Long postulateId, Long tecnlogiaId) {

        Tecnologia tecnologia = tecnologiaDao.findById(tecnlogiaId).get();
        Postulante postulante = postulanteDao.findById(postulateId).get();
        Set<Tecnologia> tecnologiaSet = postulante.getTecnologiasasignadas();
        tecnologiaSet.add(tecnologia);
        postulante.setTecnologiasasignadas(tecnologiaSet);
        return postulanteDao.save(postulante);
    }

    @Override
    public Postulante assignCityToPostulante(Long postulateId, Long ciudadId) {

        Ciudad city = ciudadDao.findById(ciudadId).get();
        Postulante postulante = postulanteDao.findById(postulateId).get();

        postulante.setCiudad(city);
        return postulanteDao.save(postulante);
    }

    @Override
    public Postulante assignEstadoToPostulante(Long postulateId, Long estadoId) {

        Estado estado = estadoDao.findById(estadoId).get();
        Postulante postulante = postulanteDao.findById(postulateId).get();

        postulante.setEstado(estado);
        return postulanteDao.save(postulante);
    }

    @Override
    public List<Postulante> buscarPorNombre(String nombre) {
        return postulanteDao.findByNombreContaining(nombre);
    }

    @Override
    public Postulante buscarPorNumeroDocumento(String numeroDocumento) {
        return postulanteDao.findByNumeroDocumento(numeroDocumento);
    }

    @Override
    public List<Postulante> buscarPorApellido(String nombre) {
        return postulanteDao.findByNombreContaining(nombre);
    }

    public List<Postulante> buscarPorEstado(Long idEstado) {
        return postulanteDao.findByEstadoId(idEstado);
    }


}
