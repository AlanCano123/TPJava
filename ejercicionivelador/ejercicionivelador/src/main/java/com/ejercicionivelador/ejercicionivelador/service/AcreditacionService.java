package com.ejercicionivelador.ejercicionivelador.service;

import com.ejercicionivelador.ejercicionivelador.exceptions.PuntoNotFoundException;
import com.ejercicionivelador.ejercicionivelador.model.Acreditacion;
import com.ejercicionivelador.ejercicionivelador.model.Punto;
import com.ejercicionivelador.ejercicionivelador.repositoy.AcreditacionRepo;
import com.ejercicionivelador.ejercicionivelador.repositoy.PuntoRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class AcreditacionService {

    private final PuntoRepo puntoRepo;
    private final AcreditacionRepo acreditacionRepo;

    public AcreditacionService(PuntoRepo puntoRepo, AcreditacionRepo acreditacionRepo) {
        this.puntoRepo = puntoRepo;
        this.acreditacionRepo= acreditacionRepo;
    }


    public Acreditacion procesarAcreditacion(Double importe, Long puntoVentaId) {
        if (importe == null || importe <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor que cero.");
        }
        if (puntoVentaId == null) {
            throw new IllegalArgumentException("El identificador del punto de venta no puede ser nulo.");
        }

        String nombrePuntoVenta = puntoRepo.findById(puntoVentaId)
                .map(Punto::getNombre)
                .orElseThrow(() -> new PuntoNotFoundException(puntoVentaId.toString()));



        Acreditacion acreditacion = new Acreditacion();
        acreditacion.setImporte(importe);
        acreditacion.setPuntoVentaId(puntoVentaId);
        acreditacion.setFechaRecepcion(LocalDate.now());
        acreditacion.setNombrePuntoVenta(nombrePuntoVenta);

        return acreditacionRepo.save(acreditacion);
    }

    public List<Acreditacion> listarAcreditaciones() {
        return acreditacionRepo.findAll();
    }

}
