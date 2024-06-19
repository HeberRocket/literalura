package com.challenge.luteralura2.repository;

import com.challenge.luteralura2.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    // Método para obtener la lista de autores con detalles de los libros registrados
    @Query("SELECT l FROM Libro l")
    List<Libro> librooos();

    @Query("SELECT s FROM Libro s WHERE s.fechasNacimiento <= :fechaBuscarVivos AND s.fechasMuerte >= :fechaBuscarVivos")
    List<Libro> autoresVivos(Year fechaBuscarVivos);


    @Query("SELECT s FROM Libro s WHERE idioma = :idiomaSeleccionado")
    List<Libro> libroIdiomas(String idiomaSeleccionado);


    boolean existsByTitulo(String titulo);
}


