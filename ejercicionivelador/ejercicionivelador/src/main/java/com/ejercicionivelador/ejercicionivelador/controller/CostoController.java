package com.ejercicionivelador.ejercicionivelador.controller;

import com.ejercicionivelador.ejercicionivelador.model.Costo;
import com.ejercicionivelador.ejercicionivelador.model.ResultadoCostoMinimo;
import com.ejercicionivelador.ejercicionivelador.service.CostoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/costos")
public class CostoController {

    private final CostoService costoService;
    public CostoController(CostoService costoService) {
        this.costoService = costoService;
    }

    //(1) Cargar un nuevo costo entre un punto de venta A y un punto de venta B
    // (crearía un camino directo entre A y B con el costo indicado)
    @PostMapping
    public Costo crear(@RequestBody Costo costo) {
        return costoService.createCosto(costo);
    }

   // (2) Remover un costo entre un punto de venta A y un punto de venta B
            //(removería en caso de existir un camino directo entre A y B)
    @DeleteMapping("/delete/{ida}/{vuelta}")
    public ResponseEntity<Void> eliminar(@PathVariable Long ida, @PathVariable Long vuelta){
        costoService.deleteCosto(ida,vuelta);
        return ResponseEntity.ok().build();
    }

//(3) Consultar los puntos de venta directamente a un punto de venta A, y los costos que implica llegar a ellos
    @GetMapping("/rutas/{punto}")
    public ResponseEntity<List<Costo>>  obtenerRutasEntrePuntos(
            @PathVariable Long punto) {
        List<Costo> respuesta = costoService.obtenerRutasDesdePunto(punto);
        return ResponseEntity.ok(respuesta);
    }

     // (4) Consultar el camino con costo mínimo entre dos puntos de venta A y B.
       // (indicar el costo mínimo, y el camino realizado, aprovechando los nombres de los puntos de venta del caché del punto anterior)
    @GetMapping("/minimo")
    public ResponseEntity<Map<String, Object>> obtenerCostoMinimo(
            @RequestParam Long ida,
            @RequestParam Long vuelta) {
        ResultadoCostoMinimo resultado = costoService.calcularCostoMinimo(ida, vuelta);

        if (resultado.getCostoMinimo() == -1) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("costoMinimo", resultado.getCostoMinimo());
        respuesta.put("ruta", resultado.getRuta());

        return ResponseEntity.ok(respuesta);
    }
}
