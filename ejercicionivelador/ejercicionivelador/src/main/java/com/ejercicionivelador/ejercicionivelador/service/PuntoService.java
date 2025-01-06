package com.ejercicionivelador.ejercicionivelador.service;

import com.ejercicionivelador.ejercicionivelador.exceptions.NoPuntosFoundException;
import com.ejercicionivelador.ejercicionivelador.exceptions.PuntoAlreadyExistsException;
import com.ejercicionivelador.ejercicionivelador.exceptions.PuntoNotFoundException;
import com.ejercicionivelador.ejercicionivelador.model.Punto;
import com.ejercicionivelador.ejercicionivelador.repositoy.PuntoRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PuntoService {
   private final PuntoRepo puntoRepo;

    public PuntoService(PuntoRepo puntoRepo) {
        this.puntoRepo = puntoRepo;
    }

    public List<Punto> getPuntos() {
        List<Punto> puntos = puntoRepo.findAll();
        if (puntos.isEmpty()) {
            throw new NoPuntosFoundException("");
        }
        return puntos;
    }

    public Optional<Punto> getPuntoById(Long id) {
    return Optional.ofNullable(puntoRepo.findById(id)
            .orElseThrow(() -> new PuntoNotFoundException(id.toString())));
    }

    public Punto addPunto(Punto punto) {
        if (punto == null) {
            throw new IllegalArgumentException("El punto no puede ser nulo.");
        }
        if (punto.getId() != null && puntoRepo.existsById(punto.getId())) {
            throw new PuntoAlreadyExistsException(punto.getId().toString());
        }
        return puntoRepo.save(punto);
    }
    public Punto updatePunto(Long id,Punto punto) {
        if (punto == null) {
            throw new IllegalArgumentException("El punto no puede ser nulo.");
        }
        if (!puntoRepo.existsById(id)) {
            throw new PuntoNotFoundException(id.toString());
        }
        punto.setId(id);
        return puntoRepo.save(punto);
    }
    public void deletePunto(Long id) {
        if (!puntoRepo.existsById(id)) {
            throw new PuntoNotFoundException(id.toString());
        }
        puntoRepo.deleteById(id);
    }
}

