package com.correo.serviciousuarios.controllers;

import com.correo.serviciousuarios.model.ApiResponse;
import com.correo.serviciousuarios.model.UsuarioDTO;
import com.correo.serviciousuarios.model.UsuarioResponse;
import com.correo.serviciousuarios.services.UsuarioService;
import com.correo.serviciousuarios.services.impls.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping(value = "/{usuarioId}")
    public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable("usuarioId") Long usuarioId) {

        UsuarioResponse usuarioResponse = usuarioService.findUsuarioById(usuarioId);

        return ResponseEntity.ok(usuarioResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        Long usuarioResponseId = usuarioService.createUsuario(usuarioDTO);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/usuarios/{usuarioId}")
            .buildAndExpand(usuarioResponseId).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "Usuario creado correctamente"));
    }

    @PutMapping
    public ResponseEntity<Void> updateUsuario(@RequestParam("usuarioId") Long usuarioId,
                                              @RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.updateUsuario(usuarioId, usuarioDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUsuario(@RequestParam("usuarioId") Long usuarioId) {
        usuarioService.deleteUsuario(usuarioId);

        return ResponseEntity.ok().build();
    }
}
