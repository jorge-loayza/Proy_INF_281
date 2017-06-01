package com.example.koko.lapazreciclaje.Objetos;

import java.io.Serializable;

/**
 * Created by koko on 18-05-17.
 */

public class Material implements Serializable {
    private String nombre,descripcion,categoria,imagen;

    public Material(String nombre, String descripcion, String categoria, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.imagen = imagen;
    }

    public Material() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
