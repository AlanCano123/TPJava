package com.ejercicionivelador.ejercicionivelador.model;

import java.util.List;

public class ResultadoCostoMinimo {
    private Integer costoMinimo;
    private List<String> ruta;

    public ResultadoCostoMinimo(Integer costoMinimo, List<String> ruta) {
        this.costoMinimo = costoMinimo;
        this.ruta = ruta;
    }

    public Integer getCostoMinimo() {
        return costoMinimo;
    }

    public List<String> getRuta() {
        return ruta;
    }

    public void setCostoMinimo(Integer costoMinimo) {
        this.costoMinimo = costoMinimo;
    }
}
