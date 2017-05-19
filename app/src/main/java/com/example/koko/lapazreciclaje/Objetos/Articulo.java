package com.example.koko.lapazreciclaje.Objetos;

/**
 * Created by koko on 18-05-17.
 */

public class Articulo {
    private String titulo,decripcion,contenido,imagen,id_usuario;

    public Articulo() {
    }


    public String getTitulo() {
        return titulo;
    }

    public Articulo(String titulo, String decripcion, String contenido, String imagen, String id_usuario) {
        this.titulo = titulo;
        this.decripcion = decripcion;
        this.contenido = contenido;
        this.imagen = imagen;
        this.id_usuario = id_usuario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
