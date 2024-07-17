package com.aluracursos.LiterAlura.service;

public interface IConvierteDatos {

    <T> T convierte(String json, Class<T> clase);

}
