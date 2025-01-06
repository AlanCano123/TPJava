package com.ejercicionivelador.ejercicionivelador.controller;

import com.ejercicionivelador.ejercicionivelador.model.Punto;
import com.ejercicionivelador.ejercicionivelador.service.PuntoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puntos")
public class PuntoController {

    private final PuntoService puntoService;
    public PuntoController(PuntoService puntoService) {
        this.puntoService = puntoService;
    }

   // (1) Recuperar todos los puntos de venta presentes en el cache
    @GetMapping
    public List<Punto> listar() {
        return puntoService.getPuntos();
    }

    //(2) Ingresar un nuevo punto de venta
    @PostMapping
    public ResponseEntity<Punto>  crear(@RequestBody Punto punto) {
        Punto punto1 = puntoService.addPunto(punto);
        return  ResponseEntity.ok(punto1);
    }

    //(3) Actualizar un punto de venta
    @PutMapping("/update/{id}")
    public ResponseEntity<Punto> actualizar(@PathVariable Long id ,@RequestBody Punto punto) {
        if(puntoService.getPuntoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(puntoService.updatePunto(id,punto));
    }

    //(4) Borrar un punto de venta
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if(puntoService.getPuntoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        puntoService.deletePunto(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo auxiliar para obtener un punto a traves de un id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Punto>  buscarPorId(@PathVariable Long id) {
        return puntoService.getPuntoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
