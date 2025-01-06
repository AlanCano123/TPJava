package com.ejercicionivelador.ejercicionivelador.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name="acreditacion")
public class Acreditacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double importe;

    private Long puntoVentaId;

    private LocalDate fechaRecepcion;

    private String nombrePuntoVenta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getNombrePuntoVenta() {
        return nombrePuntoVenta;
    }

    public void setNombrePuntoVenta(String nombrePuntoVenta) {
        this.nombrePuntoVenta = nombrePuntoVenta;
    }

    public LocalDate  getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate  fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Long getPuntoVentaId() {
        return puntoVentaId;
    }

    public void setPuntoVentaId(Long puntoVentaId) {
        this.puntoVentaId = puntoVentaId;
    }

    public Acreditacion() {
    }

    public Acreditacion(Long id, Double importe, Long puntoVentaId, LocalDate  fechaRecepcion, String nombrePuntoVenta) {
        this.id = id;
        this.importe = importe;
        this.puntoVentaId = puntoVentaId;
        this.fechaRecepcion = fechaRecepcion;
        this.nombrePuntoVenta = nombrePuntoVenta;
    }
}
