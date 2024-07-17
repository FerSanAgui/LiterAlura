package com.aluracursos.LiterAlura.repository;

import com.aluracursos.LiterAlura.model.Idioma;
import com.aluracursos.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Libro findLibroByTitulo(String nombre);
    //List<Libro> findLibroByIdioma(Idioma idioma);
    List<Libro> findAll();
}
