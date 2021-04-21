package com.correo.serviciousuarios.services;

import com.correo.serviciousuarios.model.UsuarioDTO;
import com.correo.serviciousuarios.model.UsuarioResponse;

public interface UsuarioService {

    UsuarioResponse findUsuarioById(Long usuarioId);

    Long createUsuario(UsuarioDTO usuarioDTO);

    void updateUsuario(Long usuarioId, UsuarioDTO usuarioDTO);

    void deleteUsuario(Long usuarioId);
}
