package com.challenge.luteralura2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBusqueda(
        //@JsonAlias("count")Integer numero,
        @JsonAlias("results") List<DatosLibro> datosLibros

) {
}
