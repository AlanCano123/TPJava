package com.ejercicionivelador.ejercicionivelador.exceptions;

public class DuplicateCostoException extends RuntimeException  {
    public DuplicateCostoException() {
        super("Ya existe un costo para esta combinación de ida y vuelta.");
    }
}
