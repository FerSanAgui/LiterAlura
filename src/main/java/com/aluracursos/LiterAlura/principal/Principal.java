package com.aluracursos.LiterAlura.principal;

import com.aluracursos.LiterAlura.model.*;
import com.aluracursos.LiterAlura.repository.AutorRepository;
import com.aluracursos.LiterAlura.repository.LibroRepository;
import com.aluracursos.LiterAlura.service.ConsumoApi;
import com.aluracursos.LiterAlura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<Autor> autores;
    private List<Libro> libros;
    String json = null;
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public void muestraElMenu(){

        json =consumoApi.obtenerDatos(URL_BASE);
        System.out.println("Json resultados: " + json);
        var datos = conversor.convierte(json, Datos.class);
        System.out.println("Datos resultados: " + datos);

        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    ______________________________________________
                    Elija la opción a traves de un número:
                    1 - Consulta por titulo del libro
                    2 - Listardo de libros registrados
                    3 - Listado de Autores registrados
                    4 - Listado de Autores vivos por año
                    5 - Listado de Libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();//InputMismatchException*textos
            teclado.nextLine();

            switch (opcion) {

                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarPorAutor();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación!");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }



    private Datos obtenerDatos() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
       // System.out.println("nombre " + nombreLibro);
        var json = consumoApi.obtenerDatos(URL_BASE+"?search="+nombreLibro.replace(" ","+"));
        System.out.println("Json libro: " + json);
        var datosBusqueda = conversor.convierte(json, Datos.class);
        return datosBusqueda;
    }

    private void buscarLibroPorTitulo(){
        Datos libro = obtenerDatos();
        if(!libro.resultados().isEmpty()){
            DatosLibros datosLibros = libro.resultados().get(0);
            DatosAutor datosAutor = datosLibros.autor().get(0);
            var libroGuardado = libroRepository.findLibroByTitulo(datosLibros.titulo());
            if(libroGuardado==null){
                var autorGuardado = autorRepository.findAutorByNombreIgnoreCase(datosAutor.nombre());
                Libro libroRegistrado;
                if(autorGuardado==null){
                    Autor autor =new Autor(datosAutor);
                    autorRepository.save(autor);
                    libroRegistrado = new Libro(datosLibros, autor);
                    System.out.println(libroRegistrado);
                }else {
                    libroRegistrado = new Libro(datosLibros, autorGuardado);
                }
                libroRepository.save(libroRegistrado);
                System.out.println(libroGuardado);
            }else {
                System.out.println("Este libro: " + libro.resultados().get(0).titulo()+" ya esta registrado");
            }
        } else {
            System.out.println("No se encontro libro");
        }
    }

    private void mostrarLibrosBuscados(){
        libros = libroRepository.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    private void mostrarPorAutor() {
        autores = autorRepository.findAll();
        autores.stream()
                .forEach(System.out::println);
    }
}
