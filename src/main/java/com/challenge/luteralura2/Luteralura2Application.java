package com.challenge.luteralura2;

import com.challenge.luteralura2.model.DatosBusqueda;
import com.challenge.luteralura2.model.DatosLibro;
import com.challenge.luteralura2.principal.Principal;
import com.challenge.luteralura2.repository.LibroRepository;
import com.challenge.luteralura2.service.ConsumoAPI;
import com.challenge.luteralura2.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Luteralura2Application implements CommandLineRunner {
	@Autowired
	private LibroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Luteralura2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);//repository
		principal.muestraElMenu();

	}
}
