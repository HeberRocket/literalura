package com.challenge.luteralura2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title")String titulo,
        @JsonAlias("languages")List<String> idioma,
        @JsonAlias("download_count")String numeroDescargas,
        @JsonAlias("authors")List<DatosAutor> datosAutor


        ) {

        public String getFormattedAutores() {
                return datosAutor.stream()
                        .map(DatosAutor::nombre)
                        .collect(Collectors.joining(", "));
        }

        public String getFormattedIdiomas() {
                return String.join(", ", idioma);
        }

}
