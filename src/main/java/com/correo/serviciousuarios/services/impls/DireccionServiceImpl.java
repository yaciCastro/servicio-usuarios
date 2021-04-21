package com.correo.serviciousuarios.services.impls;

import com.correo.serviciousuarios.exceptions.ResourceNotFoundException;
import com.correo.serviciousuarios.model.DireccionDTO;
import com.correo.serviciousuarios.model.DireccionResponse;
import com.correo.serviciousuarios.services.DireccionService;
import com.correo.serviciousuariosmodel.model.dbentities.Direccion;
import com.correo.serviciousuariosmodel.model.dbentities.Usuario;
import com.correo.serviciousuariosmodel.repositories.DireccionRepository;
import com.correo.serviciousuariosmodel.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public DireccionServiceImpl(DireccionRepository direccionRepository,
                                UsuarioRepository usuarioRepository) {
        this.direccionRepository = direccionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public DireccionResponse findDireccionById(Long direccionId) {
        Direccion direccion = direccionRepository.findById(direccionId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la direccion con id: " + direccionId));

        return DireccionResponse.builder()
                .usuarioId(direccion.getId())
                .direccionLinea1(direccion.getDireccionLinea1())
                .direccionLinea2(direccion.getDireccionLinea2())
                .ciudad(direccion.getCiudad())
                .provincia(direccion.getProvincia())
                .pais(direccion.getPais())
                .codigoPostal(direccion.getCodigoPostal())
                .build();
    }


    @Override
    public Long createDireccion(DireccionDTO direccionDTO) {

        Usuario usuario = usuarioRepository.findById(direccionDTO.getUsuarioId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + direccionDTO.getUsuarioId()));

        Direccion direccion = Direccion.builder()
            .usuario(usuario)
            .direccionLinea1(direccionDTO.getDireccionLinea1())
            .direccionLinea2(direccionDTO.getDireccionLinea2())
            .ciudad(direccionDTO.getCiudad())
            .provincia(direccionDTO.getProvincia())
            .pais(direccionDTO.getPais())
            .codigoPostal(direccionDTO.getCodigoPostal())
            .build();

        if (usuario.getDirecciones() == null) {
            usuario.setDirecciones(new HashSet<>());
        }
        usuario.getDirecciones().add(direccion);

        Direccion direccionResponse = direccionRepository.save(direccion);

        return direccionResponse.getId();
    }

    @Override
    public void updateDireccion(Long direccionId, DireccionDTO direccionDTO) {

        Direccion direccion = direccionRepository.findById(direccionId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la direccion con id: " + direccionId));

        if (direccionDTO.getDireccionLinea1() != null) {
            direccion.setDireccionLinea1(direccionDTO.getDireccionLinea1());
        }

        if (direccionDTO.getDireccionLinea2() != null) {
            direccion.setDireccionLinea2(direccionDTO.getDireccionLinea2());
        }

        if (direccionDTO.getCiudad() != null) {
            direccion.setCiudad(direccionDTO.getCiudad());
        }

        if (direccionDTO.getProvincia() != null) {
            direccion.setProvincia(direccionDTO.getProvincia());
        }

        if (direccionDTO.getPais() != null) {
            direccion.setPais(direccionDTO.getPais());
        }
        if (direccionDTO.getCodigoPostal() != null) {
            direccion.setCodigoPostal(direccionDTO.getCodigoPostal());
        }

        direccionRepository.save(direccion);
    }

    @Override
    public void deleteDireccion(Long direccionId) {
        direccionRepository.deleteById(direccionId);
    }

    @Override
    public Page<DireccionResponse> findAllByUsuarioId(Long usuarioId, Pageable pageable) {
        Page<Direccion> direcciones =  direccionRepository.findAllByUsuarioId(usuarioId, pageable);
        Page<DireccionResponse> resultado = direcciones.map(direccion -> {
            DireccionResponse direccionResponse = DireccionResponse.builder()
                    .id(direccion.getId())
                    .direccionLinea1(direccion.getDireccionLinea1())
                    .direccionLinea2(direccion.getDireccionLinea2())
                    .direccionPrincipal(direccion.isDireccionPrincipal())
                    .ciudad(direccion.getCiudad())
                    .pais(direccion.getPais())
                    .provincia(direccion.getProvincia())
                    .codigoPostal(direccion.getCodigoPostal())
                    .usuarioId(direccion.getUsuario().getId())
                    .build();
            return direccionResponse;
        });

        return resultado;
    }
}


