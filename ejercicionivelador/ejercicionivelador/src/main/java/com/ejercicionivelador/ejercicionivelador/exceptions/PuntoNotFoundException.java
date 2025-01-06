package com.ejercicionivelador.ejercicionivelador.exceptions;

public class PuntoNotFoundException extends RuntimeException {
    public PuntoNotFoundException(String message) {
        super("No se encontraron puntos en la base de datos."+message);
    }
}