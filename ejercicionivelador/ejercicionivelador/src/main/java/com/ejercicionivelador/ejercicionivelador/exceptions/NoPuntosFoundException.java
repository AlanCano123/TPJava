package com.ejercicionivelador.ejercicionivelador.exceptions;

public class NoPuntosFoundException extends RuntimeException {
    public NoPuntosFoundException(String message) {
        super("Punto con ID: " + message + " no encontrado.");
    }
}
