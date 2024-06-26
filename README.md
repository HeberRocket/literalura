# Descripción del Programa LITERALURA


Este repositorio alberga el proyecto finalizado como parte del desafió del programa Alura, donde se desarrolló una aplicación utilizando Java, el framework Spring y el IDE IntelliJ IDEA, integrándose con una base de datos PostgreSQL. Este programa, diseñado como una aplicación de consola, permite la búsqueda y gestión de información sobre libros, aprovechando una serie de clases y servicios especializados. A continuación, se detalla su funcionamiento:

## Estructura y Componentes Principales

### Clases y Paquetes:

- **Principal**: Contiene el menú principal y la lógica para interactuar con el usuario.
- **DatosBusqueda**, **DatosLibro**, **Libro**, **DatosAutor**: Modelos que representan la estructura de datos de los libros y autores.
- **LibroRepository**: Interfaz para la persistencia de datos.
- **ConsumoAPI**: Servicio para consumir la API externa.
- **ConvierteDatos**: Servicio para convertir los datos JSON a objetos Java.

## Funcionamiento del Programa

### Clase Principal:

Contiene los métodos principales para interactuar con el usuario a través de un menú de opciones.

#### Métodos principales:

- `muestraElMenu()`: Despliega el menú y maneja la lógica de navegación entre las opciones.
  
  ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/menu.png)
   
- `buscarLibro()`: Permite buscar libros por título a través de una API externa y guardar los resultados en la base de datos.
- `listarLibros()`, `listarAutores()`, `listarAutoresVivos()`, `listarPorIdioma()`: Métodos para listar libros y autores basados en diferentes criterios.

### Menú de Opciones:

- Opciones para buscar libros, listar libros registrados, listar autores, listar autores vivos en un año determinado y listar libros por idioma.
- El usuario interactúa con el menú mediante la entrada en la consola.

### Búsqueda y Conversión de Datos:

- `getDatosBusqueda()`: Solicita al usuario el título del libro, consume la API externa para obtener datos en formato JSON y los convierte a objetos `DatosBusqueda`.
- `buscarLibro()`: Utiliza `getDatosBusqueda()` para obtener los datos de búsqueda, los convierte en objetos `Libro`, y los guarda en la base de datos si no existen previamente.
  
  ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/buscar.png)

### Persistencia de Datos:

- **LibroRepository**: Interfaz que extiende de `JpaRepository`, permitiendo operaciones CRUD en la base de datos para la entidad `Libro`.
- Métodos como `existsByTitulo()`, `findAll()`, `librooos()`, `autoresVivos()`, `libroIdiomas()`: Métodos personalizados para consultas específicas.

### Listados y Filtrados:

- `listarLibros()`: Imprime todos los libros registrados en la base de datos.
  
 ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/listarLibro.png)
 
- `listarAutores()`: Agrupa los libros por autores y muestra los detalles de cada autor y sus libros.
  
 ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/ListarAutores.png)
 
- `listarAutoresVivos()`: Filtra y muestra los autores que estaban vivos en un año específico proporcionado por el usuario.
- `listarPorIdioma()`: Filtra y muestra los libros según el idioma especificado por el usuario.
  
  ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/listarIdioma.png)

## Ejecución del Programa

### Inicio del Programa:

- Se crea una instancia de `Principal` con un repositorio de libros.
- Se llama al método `muestraElMenu()` para comenzar la interacción con el usuario.

### Interacción con el Usuario:

- El usuario selecciona opciones del menú.
- Dependiendo de la opción, se ejecutan diferentes métodos para realizar búsquedas, listar información, y mostrar resultados en la consola.

### Consumir API y Manejar Datos:

- Para la opción de buscar libros, se consume la API externa de Gutendex para obtener datos JSON, que luego se convierten en objetos y se guardan en la base de datos si no existen previamente.
- Para listar información, se consultan los datos directamente desde la base de datos y se formatean para mostrar en la consola.
- El LibroRepository facilita la interacción con la base de datos para realizar operaciones sobre la entidad Libro, utilizando tanto métodos CRUD predefinidos de JpaRepository como consultas JPQL personalizadas. Esto permite una gestión eficiente y estructurada de los datos de libros en la aplicación.

## Ejemplo de Uso

### Buscar Libro por Título:

- Usuario elige la opción 1.
- Introduce el título del libro.
- El programa busca el libro en la API, muestra los detalles y lo guarda en la base de datos si es nuevo.
  
 ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/prueba1.png)
 
### Listar Libros Registrados:

- Usuario elige la opción 2.
- El programa consulta la base de datos y lista todos los libros registrados.
  
 ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/prueba2.png)
 
### Listar Autores Registrados:

- Usuario elige la opción 3.
- El programa filtra y muestra los autores registrados a la vez de que muestra su fecha de nacimiento, muerte y los libros de los cuales es autor.
  
  ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/prueba3.png)
  
### Listar Autores Vivos en un Año Determinado:

- Usuario elige la opción 4.
- Introduce el año de interés.
- El programa filtra y muestra los autores que estaban vivos en ese año.
  
 ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/prueba4.png)
 
### Selección de la Opción de Listar por Idioma:

- El usuario selecciona la opción 5 para listar libros por idioma.
- El usuario ingresa el idioma deseado, en este caso, "es" para Español.
- El programa verifica si el idioma ingresado es válido y luego busca libros en la base de datos en ese idioma. En este caso encuentra libros en español:
 
 ![Menu](https://github.com/HeberRocket/literalura/blob/main/imgns_literalura/prueba5.png)

---

Este programa es una combinación de un cliente de consola y un backend que interactúa con una API externa y una base de datos, permitiendo la búsqueda, almacenamiento y gestión de información de libros y autores.
