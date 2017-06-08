package com.example.koko.lapazreciclaje.Objetos;

import java.io.Serializable;

/**
 * Created by koko on 18-05-17.
 */

public class Articulo implements Serializable{
    private String titulo,decripcion,imagen,id_usuario,id_articulo,pdf;

    public Articulo() {
    }

    public Articulo(String titulo, String decripcion, String imagen, String id_usuario, String id_articulo, String pdf) {
        this.titulo = titulo;
        this.decripcion = decripcion;
        this.imagen = imagen;
        this.id_usuario = id_usuario;
        this.id_articulo = id_articulo;
        this.pdf = pdf;
    }

    public String getTitulo() {
        return titulo;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(String id_articulo) {
        this.id_articulo = id_articulo;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
