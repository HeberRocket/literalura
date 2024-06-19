package com.challenge.luteralura2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private List<String> autor;
    private String idioma;
    private Integer numeroDescargas;
    private Year fechasNacimiento;
    private Year fechasMuerte;

    public Libro() {

    }

    public Libro(List<DatosLibro> datosLibros) {        //List<DatosLibro> datosLibros, DatosLibro d
        DatosLibro d = datosLibros.get(0);
        this.autor = d.datosAutor().stream()
                .map(DatosAutor::nombre)
                .collect(Collectors.toList());
        this.titulo = d.titulo();
        this.idioma = String.join(", ", d.idioma());
        this.numeroDescargas = Integer.valueOf(d.numeroDescargas());
        this.fechasNacimiento = parseYear(d.datosAutor().stream()
                .map(DatosAutor::fechaNacimiento)
                .map(String::valueOf)
                .findFirst()
                .orElse(null));
        this.fechasMuerte = parseYear(d.datosAutor().stream()
                .map(DatosAutor::fechaMuerte)
                .map(String::valueOf)
                .findFirst()
                .orElse(null));
    }
    private Year parseYear(String fecha) {
        if (fecha == null || fecha.isEmpty()) {
            return null;
        }
        try {
            return Year.parse(fecha, DateTimeFormatter.ofPattern("yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Error al parsear la fecha: " + fecha + " - " + e.getMessage());
            return null;
        }
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getAutor() {
        return autor;
    }

    public void setAutor(List<String> autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Year getFechasNacimiento() {
        return fechasNacimiento;
    }

    public void setFechasNacimiento(Year fechasNacimiento) {
        this.fechasNacimiento = fechasNacimiento;
    }

    public Year getFechasMuerte() {
        return fechasMuerte;
    }

    public void setFechasMuerte(Year fechasMuerte) {
        this.fechasMuerte = fechasMuerte;
    }

    @Override
    public String toString() {
        return  "----- LIBRO -----"+
                "\nTitulo: " + titulo + '\'' +
                "\nAutor='" + String.join(", ", autor) + '\'' +
                "\nIdioma: " + idioma + '\'' +
                "\nNumero de descargas: " + numeroDescargas+"\n";
    }

//    @Override
//    public String toString() {
//        return
//                "Titulo='" + titulo + '\'' +
//                "\n autor='" + String.join(", ", autor) + '\'' +
//                "\n idioma='" + idioma + '\'' +
//                "\n numeroDescargas=" + numeroDescargas +
//                "\n fechaNacimiento=" + fechasNacimiento +
//                "\n fechaMuerte=" + fechasMuerte;
//    }
}
