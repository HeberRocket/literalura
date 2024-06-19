package com.challenge.luteralura2.principal;

import com.challenge.luteralura2.model.DatosBusqueda;
import com.challenge.luteralura2.model.DatosLibro;
import com.challenge.luteralura2.model.Libro;
import com.challenge.luteralura2.repository.LibroRepository;
import com.challenge.luteralura2.service.ConsumoAPI;
import com.challenge.luteralura2.service.ConvierteDatos;
import com.challenge.luteralura2.model.DatosAutor;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "http://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private LibroRepository repositorio;

    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {

            var menu = """
                    1 - Buscar libro por titulo.
                    2 - Listar libros registrados previamente.
                    3 - Listar autores de los libros registrados previamente.
                    4 - Listar autores vivos en un determinado año.
                    5 - Listar libros por idioma.
                    0 - Salir.
                    
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {

                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");

            }



        }

    }

    private DatosBusqueda getDatosBusqueda(){
        System.out.println("Escribe el libro que deseas buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE+nombreLibro.replace(" ","%20"));
        List<DatosBusqueda> busquedas = new ArrayList<>();
        var datosBusqueda = conversor.obtenerDatos(json, DatosBusqueda.class);
        return datosBusqueda;

    }



    private void buscarLibro() {
        DatosBusqueda datosBusqueda = getDatosBusqueda();
        List<DatosLibro> listaDeLibros = datosBusqueda.datosLibros();

        if (listaDeLibros != null && !listaDeLibros.isEmpty()) {
            Libro libro = new Libro(listaDeLibros);
            if (!repositorio.existsByTitulo(libro.getTitulo())){
                repositorio.save(libro);
                datosLibros.addAll(listaDeLibros);
                System.out.println(formatearLibro(listaDeLibros.get(0)));
            }else {
                System.out.println("El libro con el título '" + libro.getTitulo() + "' ya existe en la base de datos.");
            }
        } else {
            System.out.println("No se encontraron libros con ese nombre.");
        }
    }




    private String formatearLibro(DatosLibro libro) {
        StringBuilder sb = new StringBuilder();
        sb.append("----- LIBRO -----\n")
                .append("Titulo: ").append(libro.titulo()).append("\n")
                .append("Autor: ").append(libro.datosAutor().stream()
                        .map(DatosAutor::nombre)
                        .collect(Collectors.joining(", ")))
                .append("\nIdioma: ").append(String.join(", ", libro.idioma())).append("\n")
                .append("Numero de descargas: ").append(libro.numeroDescargas()).append("\n");
        return sb.toString();
    }



    private void listarLibros() {
        List<Libro> libros = repositorio.findAll();

        libros.stream()
                .forEach(System.out::println);

    }



    private void listarAutores() {
        // Obtener todos los libros
        List<Libro> libros = repositorio.librooos();
        // Agrupar libros por autor
        Map<String, List<Libro>> autoresConLibros = libros.stream()
                .flatMap(libro -> libro.getAutor().stream().map(autor -> Map.entry(autor, libro)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        System.out.println("*** Autores y sus libros registrados ***");
        // Iterar sobre el mapa y mostrar la información
        autoresConLibros.forEach((autor, listaLibros) -> {
            // Obtener la primera instancia de fechas de nacimiento y fallecimiento
            Libro primerLibro = listaLibros.get(0);
            Year fechaNacimiento = primerLibro.getFechasNacimiento();
            Year fechaFallecimiento = primerLibro.getFechasMuerte();

            System.out.println("\n\tAutor: " + autor);
            System.out.println("\tFecha de nacimiento: " + (fechaNacimiento != null ? fechaNacimiento : "Desconocida"));
            System.out.println("\tFecha de fallecimiento: " + (fechaFallecimiento != null ? fechaFallecimiento : "Desconocida"));
            System.out.print("\tLibro: ");
            listaLibros.forEach(libro -> {
                System.out.print(libro.getTitulo() + ", ");
            });
            System.out.println("");
        });
        System.out.println("");
    }



    private void listarAutoresVivos() {

        System.out.println("En que año quieres consultar si hay autores vivos? ");
        int fecha = teclado.nextInt();
        teclado.nextLine();
        Year fechaYear = Year.of(fecha); // Convertir int a Year
        List<Libro> listarVivos = repositorio.autoresVivos(fechaYear);
        if (listarVivos.isEmpty()){
            System.out.println("No se encontraron autores vivos en el año "+fechaYear);
        }else{
            listarVivos.forEach(libro ->
                    System.out.println("Autor: " + String.join(", ",libro.getAutor()) +//String.join(", ", autor)
                            "\nFecha de nacimiento: " + libro.getFechasNacimiento() +
                            "\nFecha de fallecimiento: "+ libro.getFechasMuerte() +
                            "\nLibro: "+ libro.getTitulo() +"\n"));
        }

    }

    private void listarPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros:
                es - Español
                en - Inglés
                fr - Frances
                pt - Portugués
                """);
        String idioma = teclado.nextLine();

        // Verificar si el idioma es válido
        if (idioma.equals("es") || idioma.equals("en") || idioma.equals("fr") || idioma.equals("pt")) {
            List<Libro> idiomas = repositorio.libroIdiomas(idioma);

            if (idiomas.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma: " + idioma);
            } else {
                idiomas.forEach(System.out::println);
            }
        } else {
            System.out.println("Idioma no válido. Por favor, ingrese uno de los siguientes: es, en, fr, pt.");
        }

    }

}
