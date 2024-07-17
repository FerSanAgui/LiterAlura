package com.aluracursos.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalInt;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = OptionalInt.of(Integer.valueOf(datosAutor.fechaDeNacimiento())).orElse(0);
        this.fechaDeFallecimiento = OptionalInt.of(Integer.valueOf(datosAutor.fechaDeMuerte())).orElse(0);
    }

    @Override
    public String toString() {
        return
                "-------AUTOR-------\n"+
                "Autor: " + nombre + "\n" +
                "Fecha de Nacimiento: " + fechaDeNacimiento + "\n" +
                "Fecha de Fallecimiento: " + fechaDeFallecimiento + "\n" +
                "Libros: [" + libros + "]\n\n";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        //libros.forEach(l -> l.setAutor(this));
        this.libros = libros;
    }
}
