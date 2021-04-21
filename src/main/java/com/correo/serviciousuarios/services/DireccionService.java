package com.correo.serviciousuarios.services;

import com.correo.serviciousuarios.model.DireccionDTO;
import com.correo.serviciousuarios.model.DireccionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DireccionService {

    DireccionResponse findDireccionById(Long direccionId);


    Long createDireccion(DireccionDTO direccionDTO);


    void updateDireccion(Long direccionId, DireccionDTO direccionDTO);


    void deleteDireccion(Long direccionId);


    Page<DireccionResponse> findAllByUsuarioId(Long userId, Pageable pageable);


}
