package com.correo.serviciousuarios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DireccionDTO {

    private String direccionLinea1;
    private String direccionLinea2;
    private String ciudad;
    private String provincia;
    private String pais;
    private String codigoPostal;

    private boolean direccionPrincipal;

    private Long usuarioId;
}
