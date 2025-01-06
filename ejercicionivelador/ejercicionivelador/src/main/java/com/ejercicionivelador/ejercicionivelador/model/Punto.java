package com.ejercicionivelador.ejercicionivelador.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "punto")
public class Punto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Punto() {
    }

    public Punto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Punto(Long id) {
        this.id = id;
    }
}
