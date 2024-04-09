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
        return (List) postulanteDao.findAll();
    }

    @Override
    @Transactional
    public Postulante savePostulante(PostulanteDto PostulanteDto) throws IOException {
        System.out.print("here");
        System.out.println(PostulanteDto.getFilesMultipart());
        Postulante postulante = modelMapper.map(PostulanteDto, Postulante.class);
        postulante.setTecnologiasasignadas(new HashSet<>());
        postulanteDao.save(postulante);
        System.out.println(PostulanteDto.getFilesMultipart());


        if (PostulanteDto.getTecnologiasList() != null) {
            for (Long tecnologiaId : PostulanteDto.getTecnologiasList()) {
                assignTecnologiaToPostulante(postulante.getId_postulante(), tecnologiaId);
            }
        }

        if (PostulanteDto.getFilesMultipart() != null) {
            System.out.println("files Found");
            System.out.println(PostulanteDto.getFilesMultipart().size());
            List<File> files = new ArrayList<>();
            for (MultipartFile file : PostulanteDto.getFilesMultipart()) {
                InputStream fileInputStream = file.getInputStream();
                String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                Path directoriImagenes = Paths.get("cv/" + DigestUtils.md5DigestAsHex(fileInputStream) + fileExtension);
                String rutaAbsoluta = directoriImagenes.toFile().getAbsolutePath();
                try {
                    byte[] bytesImg = file.getBytes();
                    Path rutaCompleta = Paths.get(rutaAbsoluta);
                    Files.write(rutaCompleta, bytesImg);
                } catch (IOException e) {
                    System.out.println("Error al subir el archivo");
                }
                File f = new File();
                String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                String fileUrl = baseUrl + "/" + directoriImagenes.toString().replace("\\", "/");
                f.setLinkToFile(fileUrl);
                f.setFile_type(fileExtension);
                files.add(f);
            }
            postulante.setFiles(files);
        } else {
            postulante.setFiles(new ArrayList<>());
        }

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
    @Transactional
    public Postulante updatePostulante(Long id, PostulanteDto postulanteDto) throws IOException {
        Optional<Postulante> optionalPostulante = postulanteDao.findById(id);

        if (optionalPostulante.isPresent()) {
            Postulante postulante = optionalPostulante.get();
            postulanteDto.setId_postulante(id);
            modelMapper.map(postulanteDto, postulante);
            postulante.setTecnologiasasignadas(new HashSet<>());
            postulanteDao.save(postulante);

            if (postulanteDto.getTecnologiasList() != null) {
                for (Long tecnologiaId : postulanteDto.getTecnologiasList()) {
                    assignTecnologiaToPostulante(postulante.getId_postulante(), tecnologiaId);
                }
            }

            if (postulanteDto.getFilesMultipart() != null) {
                List<File> files = new ArrayList<>();
                files = postulante.getFiles();


                if (postulanteDto.getFilesMultipart() != null) {
                    for (MultipartFile file : postulanteDto.getFilesMultipart()) {
                        InputStream fileInputStream = file.getInputStream();
                        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                        Path directoriImagenes = Paths.get("cv/" + DigestUtils.md5DigestAsHex(fileInputStream) + fileExtension);
                        String rutaAbsoluta = directoriImagenes.toFile().getAbsolutePath();
                        try {
                            byte[] bytesImg = file.getBytes();
                            Path rutaCompleta = Paths.get(rutaAbsoluta);
                            Files.write(rutaCompleta, bytesImg);
                        } catch (IOException e) {
                            System.out.println("Error al subir el archivo");
                        }
                        File f = new File();
                        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                        String fileUrl = baseUrl + "/" + directoriImagenes.toString().replace("\\", "/");
                        f.setLinkToFile(fileUrl);
                        f.setFile_type(fileExtension);
                        files.add(f);
                    }
                    postulante.setFiles(files);
                }
            } else {
                postulante.setFiles(postulante.getFiles());
            }

            assignCityToPostulante(postulante.getId_postulante(), postulanteDto.getId_ciudad());
            assignEstadoToPostulante(postulante.getId_postulante(), postulanteDto.getId_estado());


            postulanteDao.save(postulante);
            System.out.println(postulante);
            return postulante;

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
        List<Postulante> postulantes = (List) postulanteDao.findAll();
        List<Postulante> resultado = new ArrayList<>();
        for (Postulante postulante : postulantes) {
            if (postulante.getNombre().contains(nombre)) {
                resultado.add(postulante);
            }
        }
        return resultado;
    }


    @Override
    public List<Postulante> buscarPorApellido(String apellido) {
        List<Postulante> postulantes = (List) postulanteDao.findAll();
        List<Postulante> resultado = new ArrayList<>();
        for (Postulante postulante : postulantes) {
            if (postulante.getApellido().contains(apellido)) {
                resultado.add(postulante);
            }
        }
        return resultado;
    }

    //
    public List<Postulante> buscarPorEstado(Long idEstado) {
        List<Postulante> postulantes = (List) postulanteDao.findAll();
        List<Postulante> resultado = new ArrayList<>();
        for (Postulante postulante : postulantes) {
            if (Objects.equals(postulante.getEstado().id_estado, idEstado)) {
                resultado.add(postulante);
            }
        }
        return resultado;
    }

    @Override
    public List<Postulante> buscarPorDocumento(String nroDocumento) {
        List<Postulante> postulantes = (List) postulanteDao.findAll();
        List<Postulante> resultado = new ArrayList<>();
        for (Postulante postulante : postulantes) {
            if (postulante.getNombre().contains(nroDocumento)) {
                resultado.add(postulante);
            }
        }
        return resultado;
    }

    @Override
    public List<Postulante> filtro(String nombre, String apellido, Long idEstado, String nroDocumento, Long idTecnologia) {
        List<Postulante> postulantes = (List<Postulante>) postulanteDao.findAll();
        List<Postulante> resultado = new ArrayList<>();

        for (Postulante postulante : postulantes) {
            boolean match = true;

            // Check if the nombre parameter is not null and if the postulante's nombre matches
            if (nombre != null && !nombre.isEmpty() && !postulante.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                match = false;
            }

            // Check if the apellido parameter is not null and if the postulante's apellido matches
            if (apellido != null && !apellido.isEmpty() && !postulante.getApellido().toLowerCase().contains(apellido.toLowerCase())) {
                match = false;
            }

            // Check if the idEstado parameter is not null and if the postulante's idEstado matches
            if (idEstado != null && postulante.getEstado().id_estado != idEstado) {
                match = false;
            }

            // Check if the nroDocumento parameter is not null and if the postulante's nroDocumento matches
            if (nroDocumento != null && !nroDocumento.isEmpty() && !postulante.getNro_documento().equalsIgnoreCase(nroDocumento)) {
                match = false;
            }

            // Check if the idTecnologia parameter is not null and if the postulante's tecnologias contain the specified idTecnologia
            if (idTecnologia != null && postulante.getTecnologiasasignadas().stream().noneMatch(tecnologia -> tecnologia.getId_tecnologia().equals(idTecnologia))) {
                match = false;
            }


            // If any parameter is null, consider the match as true
            if (nombre == null && apellido == null && idEstado == null && nroDocumento == null && idTecnologia == null) {
                match = true;
            }

            // If all conditions are satisfied, add the postulante to the resultado list
            if (match) {
                resultado.add(postulante);
            }
        }

        return resultado;
    }
}
