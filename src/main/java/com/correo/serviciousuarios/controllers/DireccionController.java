package com.correo.serviciousuarios.controllers;

import com.correo.serviciousuarios.config.ApiPageable;
import com.correo.serviciousuarios.model.ApiResponse;
import com.correo.serviciousuarios.model.DireccionDTO;
import com.correo.serviciousuarios.model.DireccionResponse;
import com.correo.serviciousuarios.model.UsuarioResponse;
import com.correo.serviciousuarios.services.DireccionService;
import com.correo.serviciousuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {

    private final DireccionService direccionService;

    @Autowired
    public DireccionController(DireccionService direccionService) { this.direccionService = direccionService; }

    @GetMapping(value = "/{direccionId}")
    public ResponseEntity<DireccionDTO> getDireccion(@PathVariable("direccionId") Long direccionId) {

        DireccionResponse direccionResponse = direccionService.findDireccionById(direccionId);

        return ResponseEntity.ok(direccionResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createDireccion(@RequestBody DireccionDTO direccionDTO) {

        Long direccionResponseId = direccionService.createDireccion(direccionDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/direccion/{direccionId}")
                .buildAndExpand(direccionResponseId).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Direccion creada correctamente"));

    }

    @PutMapping
    public ResponseEntity<DireccionDTO> updateDireccion(@RequestBody DireccionDTO direccionDTO,
                                                        @RequestParam("direccionId") Long direccionId) {

        direccionService.updateDireccion(direccionId, direccionDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<DireccionDTO> deleteDireccion(@RequestParam("direccionId") Long direccionId) {
        direccionService.deleteDireccion(direccionId);
        return ResponseEntity.ok().build();
    }

    @ApiPageable
    @GetMapping(value = "/getAllDireccionesByUsuarioId")
    public  ResponseEntity<Page<DireccionResponse>> getAllDireccionesByUsuarioId(@RequestParam("usuarioId") Long usuarioId,
                                                                                 Pageable pageable){

        Page<DireccionResponse> direccion = direccionService.findAllByUsuarioId(usuarioId, pageable);
        return ResponseEntity.ok(direccion);
    }
}
