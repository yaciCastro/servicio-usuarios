package com.correo.serviciousuarios.services.impls;

import com.correo.serviciousuarios.exceptions.ConflictException;
import com.correo.serviciousuarios.exceptions.ResourceNotFoundException;
import com.correo.serviciousuarios.model.UsuarioDTO;
import com.correo.serviciousuarios.model.UsuarioResponse;
import com.correo.serviciousuarios.services.UsuarioService;
import com.correo.serviciousuariosmodel.model.dbentities.Usuario;
import com.correo.serviciousuariosmodel.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponse findUsuarioById(Long usuarioId) {
        // Forma 1 de manejar si un usuario esta o no en la base de datos
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (!usuario.isPresent()) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId);
        }

        return UsuarioResponse.builder()
            .id(usuario.get().getId())
            .nombre(usuario.get().getNombre())
            .apellido(usuario.get().getApellido())
            .email(usuario.get().getEmail())
            .numeroDeTelefono(usuario.get().getNumeroDeTelefono())
            .build();
    }

    @Override
    public Long createUsuario(UsuarioDTO usuarioDTO) {

        boolean existsByEmail = usuarioRepository.existsByEmail(usuarioDTO.getEmail());
        if (existsByEmail) {
            throw new ConflictException("El email " + usuarioDTO.getEmail() + " ya esta en uso");
        }

        Usuario usuario = Usuario.builder()
            .nombre(usuarioDTO.getNombre())
            .apellido(usuarioDTO.getApellido())
            .email(usuarioDTO.getEmail())
            .numeroDeTelefono(usuarioDTO.getNumeroDeTelefono())
            .passwordHash(usuarioDTO.getPasswordHash())
            .build();

        Usuario usuarioResponse = usuarioRepository.save(usuario);

        return usuarioResponse.getId();
    }

    @Override
    public void updateUsuario(Long usuarioId, UsuarioDTO usuarioDTO) {
        // Forma 2 de manejar si un usuario esta o no en la base de datos - MEJOR FORMA
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));

        // Se actualizan solo los campos que el front quiera, si un campo dto es nulo no se va a actualizar

        if (usuarioDTO.getNombre() != null) {
            usuario.setNombre(usuarioDTO.getNombre());
        }

        if (usuarioDTO.getApellido() != null) {
            usuario.setApellido(usuarioDTO.getApellido());
        }

        if (usuarioDTO.getEmail() != null) {
            usuario.setEmail(usuarioDTO.getEmail());
        }

        if (usuarioDTO.getNumeroDeTelefono() != null) {
            usuario.setNumeroDeTelefono(usuarioDTO.getNumeroDeTelefono());
        }

        if (usuarioDTO.getPasswordHash() != null) {
            usuario.setPasswordHash(usuarioDTO.getPasswordHash());
        }

        usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }
}
