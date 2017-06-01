package com.example.koko.lapazreciclaje.Objetos;

import java.io.Serializable;

/**
 * Created by koko on 25-05-17.
 */

public class LugarReciclaje implements Serializable{
    private String nombre,direccion,latitud,longitud,categoria;

    public LugarReciclaje() {
    }

    public LugarReciclaje(String nombre, String direccion, String latitud, String longitud, String categoria) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
