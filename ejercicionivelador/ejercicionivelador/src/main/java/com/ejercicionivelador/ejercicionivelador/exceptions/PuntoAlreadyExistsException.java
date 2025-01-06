package com.ejercicionivelador.ejercicionivelador.exceptions;

public class PuntoAlreadyExistsException extends RuntimeException {
    public PuntoAlreadyExistsException(String message) {
        super("El punto con ID " + message + " ya existe.");
    }
}