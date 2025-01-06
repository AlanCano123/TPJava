package com.ejercicionivelador.ejercicionivelador.controller;

import com.ejercicionivelador.ejercicionivelador.model.Acreditacion;
import com.ejercicionivelador.ejercicionivelador.service.AcreditacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acreditaciones")
public class AcreditacionController {


    AcreditacionService acreditacionService;

    public AcreditacionController(AcreditacionService acreditacionService) {
        this.acreditacionService = acreditacionService;
    }

    @GetMapping
    public List<Acreditacion> getAcreditaciones() {
        return acreditacionService.listarAcreditaciones();
    }

    @PostMapping
    public Acreditacion addAcreditacion(@RequestBody Acreditacion acreditacion) {
        return acreditacionService.procesarAcreditacion(acreditacion.getImporte(), acreditacion.getPuntoVentaId());
    }

}
