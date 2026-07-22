package com.paqueteria.model.interfaces;

import java.util.List;

public interface Crud<T> {

    boolean guardar(T objeto);

    boolean actualizar(T objeto);

    boolean eliminar(int id);

    T buscarPorId(int id);

    List<T> listar();

}