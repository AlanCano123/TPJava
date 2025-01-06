package com.ejercicionivelador.ejercicionivelador.model;

import jakarta.persistence.*;


@Entity
@Table(name = "costos")
public class Costo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ida", nullable = false)
    private Punto ida;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vuelta", nullable = false)
    private Punto vuelta;

    @Column(nullable = false)
    private Integer costo;

    public Costo(Punto ida, Punto vuelta, Integer costo) {
        this.ida = ida;
        this.vuelta = vuelta;
        this.costo = costo;
    }

    public Costo() {
    }




    public Punto getIda() {
        return ida;
    }

    public void setIda(Punto ida) {
        this.ida = ida;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public Punto getVuelta() {
        return vuelta;
    }

    public void setVuelta(Punto vuelta) {
        this.vuelta = vuelta;
    }
}