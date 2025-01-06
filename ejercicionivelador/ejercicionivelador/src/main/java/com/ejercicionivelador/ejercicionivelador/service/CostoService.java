package com.ejercicionivelador.ejercicionivelador.service;

import com.ejercicionivelador.ejercicionivelador.exceptions.DuplicateCostoException;
import com.ejercicionivelador.ejercicionivelador.exceptions.NoPuntosFoundException;
import com.ejercicionivelador.ejercicionivelador.exceptions.PuntoNotFoundException;
import com.ejercicionivelador.ejercicionivelador.model.Costo;
import com.ejercicionivelador.ejercicionivelador.model.Punto;
import com.ejercicionivelador.ejercicionivelador.model.ResultadoCostoMinimo;
import com.ejercicionivelador.ejercicionivelador.repositoy.CostoRepo;
import com.ejercicionivelador.ejercicionivelador.repositoy.PuntoRepo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CostoService {

    private final CostoRepo costoRepo;
    private final PuntoRepo puntoRepo;

    public CostoService(CostoRepo costoRepo, PuntoRepo puntoRepo) {
        this.costoRepo = costoRepo;
        this.puntoRepo = puntoRepo;
    }

    public Costo createCosto(Costo costo) {
        if (costoRepo.existsByIdaAndVuelta(costo.getIda(), costo.getVuelta())
                || costoRepo.existsByIdaAndVuelta(costo.getVuelta(), costo.getIda())
        ) {
            throw new DuplicateCostoException();
        }
        Punto ida = puntoRepo.findById(costo.getIda().getId())
                .orElseThrow(() -> new NoPuntosFoundException(costo.getIda().getId().toString()));
        Punto vuelta = puntoRepo.findById(costo.getVuelta().getId())
                .orElseThrow(() -> new NoPuntosFoundException(costo.getVuelta().getId().toString()));

        costo.setIda(ida);
        costo.setVuelta(vuelta);
        return costoRepo.save(costo);
    }

    public void deleteCosto(Long ida, Long vuelta) {

        if (ida == null || vuelta == null || ida <= 0 || vuelta <= 0) {
            throw new IllegalArgumentException("Puntos nulos, ceros o negativos no permitidos");
        }

        Punto puntoA = new Punto(ida);
        Punto puntoB = new Punto(vuelta);

        boolean caminoDirecto = costoRepo.existsByIdaAndVuelta(puntoA, puntoB);
        boolean caminoInverso = costoRepo.existsByIdaAndVuelta(puntoB, puntoA);

        if (!caminoDirecto && !caminoInverso) {
            throw new IllegalArgumentException("El costo con los puntos especificados no existe.");
        }

        if (caminoDirecto) {
            costoRepo.deleteByIdaAndVuelta(puntoA, puntoB);
        }
        if (caminoInverso) {
            costoRepo.deleteByIdaAndVuelta(puntoB, puntoA);
        }
    }

    public ResultadoCostoMinimo calcularCostoMinimo(Long ida, Long vuelta) {
        if (ida == null || vuelta == null || ida <= 0 || vuelta <= 0) {
            throw new IllegalArgumentException("Puntos nulos, ceros o negativos no permitidos.");
        }
        if (ida.equals(vuelta)) {
            return new ResultadoCostoMinimo(0, List.of(puntoRepo.findById(ida).get().getNombre()));
        }
        if (!puntoRepo.existsById(ida) || !puntoRepo.existsById(vuelta)) {
            throw new IllegalArgumentException("Uno o ambos puntos no existen.");
        }

        Optional<Costo> costo = costoRepo.findByIdaAndVuelta(ida, vuelta);
        return costo.map(value -> new ResultadoCostoMinimo(
                value.getCosto(),
                List.of(
                        puntoRepo.findById(ida).get().getNombre(),
                        puntoRepo.findById(vuelta).get().getNombre()
                )
        )).orElseGet(() -> encontrarCostoMinimo(ida, vuelta));
    }
    private ResultadoCostoMinimo encontrarCostoMinimo(Long inicio, Long destino) {
        List<Punto> puntos = puntoRepo.findAll();
        List<Costo> costos = costoRepo.findAll();

        Map<Long, Map<Long, Integer>> grafo = new HashMap<>();
        for (Costo costo : costos) {
            grafo.computeIfAbsent(costo.getIda().getId(), k -> new HashMap<>())
                    .put(costo.getVuelta().getId(), costo.getCosto());
            grafo.computeIfAbsent(costo.getVuelta().getId(), k -> new HashMap<>())
                    .put(costo.getIda().getId(), costo.getCosto());
        }

        Map<Long, Integer> distancias = new HashMap<>();
        Map<Long, Long> previos = new HashMap<>();
        Set<Long> visitados = new HashSet<>();
        PriorityQueue<Long> cola = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        for (Punto punto : puntos) {
            distancias.put(punto.getId(), Integer.MAX_VALUE);
        }
        distancias.put(inicio, 0);
        cola.add(inicio);

        while (!cola.isEmpty()) {
            Long actual = cola.poll();
            if (!visitados.add(actual)) continue;

            Map<Long, Integer> adyacentes = grafo.getOrDefault(actual, Collections.emptyMap());
            for (Map.Entry<Long, Integer> vecino : adyacentes.entrySet()) {
                Long puntoVecino = vecino.getKey();
                Integer costo = vecino.getValue();

                if (!visitados.contains(puntoVecino)) {
                    int nuevaDistancia = distancias.get(actual) + costo;
                    if (nuevaDistancia < distancias.get(puntoVecino)) {
                        distancias.put(puntoVecino, nuevaDistancia);
                        previos.put(puntoVecino, actual);
                        cola.add(puntoVecino);
                    }
                }
            }
        }
        if (!distancias.containsKey(destino) || distancias.get(destino) == Integer.MAX_VALUE) {
            return new ResultadoCostoMinimo(-1, List.of());
        }
        List<String> camino = new ArrayList<>();
        Long actual = destino;
        while (actual != null) {
            camino.add(puntoRepo.findById(actual).get().getNombre());
            actual = previos.get(actual);
        }
        Collections.reverse(camino);

        return new ResultadoCostoMinimo(distancias.get(destino), camino);
    }

    public List<Costo> obtenerRutasDesdePunto(Long puntoId) {
        if (!puntoRepo.existsById(puntoId)) {
            throw new PuntoNotFoundException(puntoId.toString());
        }
        List<Costo> costos = costoRepo.findAllByPunto(puntoId);
        if(costos.isEmpty()){
            throw new PuntoNotFoundException(puntoId.toString());
        }

        return costos;
    }

}

