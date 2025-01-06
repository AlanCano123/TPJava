package com.ejercicionivelador.ejercicionivelador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ejercicionivelador.ejercicionivelador.exceptions.*;
import com.ejercicionivelador.ejercicionivelador.model.Costo;
import com.ejercicionivelador.ejercicionivelador.model.Punto;
import com.ejercicionivelador.ejercicionivelador.model.ResultadoCostoMinimo;
import com.ejercicionivelador.ejercicionivelador.repositoy.CostoRepo;
import com.ejercicionivelador.ejercicionivelador.repositoy.PuntoRepo;
import com.ejercicionivelador.ejercicionivelador.service.CostoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class CostoServiceTest {

    @Mock
    private CostoRepo costoRepo;

    @Mock
    private PuntoRepo puntoRepo;

    @InjectMocks
    private CostoService costoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCosto_ShouldSaveCosto_WhenCostoIsValid() {
        Punto ida = new Punto(1L, "A");
        Punto vuelta = new Punto(2L, "B");
        Costo costo = new Costo(ida, vuelta, 100);
        when(puntoRepo.findById(1L)).thenReturn(Optional.of(ida));
        when(puntoRepo.findById(2L)).thenReturn(Optional.of(vuelta));
        when(costoRepo.existsByIdaAndVuelta(ida, vuelta)).thenReturn(false);
        when(costoRepo.save(costo)).thenReturn(costo);
        Costo result = costoService.createCosto(costo);
        assertNotNull(result);
        assertEquals(ida, result.getIda());
        assertEquals(vuelta, result.getVuelta());
        assertEquals(100, result.getCosto());
        verify(costoRepo, times(1)).save(costo);
    }

    @Test
    void createCosto_ShouldThrowException_WhenCostoAlreadyExists() {
        Punto ida = new Punto(1L, "A");
        Punto vuelta = new Punto(2L, "B");
        Costo costo = new Costo(ida, vuelta, 100);
        when(costoRepo.existsByIdaAndVuelta(ida, vuelta)).thenReturn(true);
        assertThrows(DuplicateCostoException.class, () -> costoService.createCosto(costo));
        verify(costoRepo, never()).save(any());
    }

    @Test
    void testDeleteCosto_BothPathsExist() {
        Punto puntoA = new Punto(1L);
        Punto puntoB = new Punto(2L);
        when(costoRepo.existsByIdaAndVuelta(any(Punto.class), any(Punto.class))).thenReturn(true);
        costoService.deleteCosto(1L, 2L);
        verify(costoRepo).deleteByIdaAndVuelta(
                argThat(p -> p.getId() == 1L),
                argThat(p -> p.getId() == 2L)
        );
        verify(costoRepo).deleteByIdaAndVuelta(
                argThat(p -> p.getId() == 2L),
                argThat(p -> p.getId() == 1L)
        );
    }

    @Test
    void deleteCosto_ShouldThrowException_WhenCostoDoesNotExist() {
        Punto ida = new Punto(1L, "A");
        Punto vuelta = new Punto(2L, "B");
        when(costoRepo.existsByIdaAndVuelta(ida, vuelta)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> costoService.deleteCosto(1L, 2L));
        verify(costoRepo, never()).deleteByIdaAndVuelta(any(), any());
    }
    @Test
    void calcularCostoMinimo_ShouldReturnCosto_WhenDirectRouteExists() {
        Punto ida = new Punto(1L, "A");
        Punto vuelta = new Punto(2L, "B");
        Costo costo = new Costo(ida, vuelta, 100);
        when(costoRepo.findAll()).thenReturn(List.of(costo));
        when(puntoRepo.findAll()).thenReturn(List.of(ida, vuelta));
        when(puntoRepo.findById(1L)).thenReturn(Optional.of(ida));
        when(puntoRepo.findById(2L)).thenReturn(Optional.of(vuelta));
        when(puntoRepo.existsById(1L)).thenReturn(true);
        when(puntoRepo.existsById(2L)).thenReturn(true);
        ResultadoCostoMinimo result = costoService.calcularCostoMinimo(1L, 2L);
        assertEquals(100, result.getCostoMinimo());
        assertEquals(List.of("A", "B"), result.getRuta());
    }

    @Test
    void calcularCostoMinimo_ShouldThrowException_WhenPuntosAreInvalid() {
        assertThrows(IllegalArgumentException.class, () -> costoService.calcularCostoMinimo(null, 2L));
        assertThrows(IllegalArgumentException.class, () -> costoService.calcularCostoMinimo(1L, null));
        assertThrows(IllegalArgumentException.class, () -> costoService.calcularCostoMinimo(-1L, 2L));
    }

    @Test
    void obtenerRutasDesdePunto_ShouldReturnListOfCostos_WhenPuntoExists() {
        Punto punto = new Punto(1L, "A");
        List<Costo> costos = List.of(
                new Costo(punto, new Punto(2L, "B"), 100),
                new Costo(punto, new Punto(3L, "C"), 200)
        );

        when(puntoRepo.existsById(1L)).thenReturn(true);
        when(costoRepo.findAllByPunto(1L)).thenReturn(costos);
        List<Costo> result = costoService.obtenerRutasDesdePunto(1L);
        assertEquals(2, result.size());
        verify(costoRepo, times(1)).findAllByPunto(1L);
    }

    @Test
    void obtenerRutasDesdePunto_ShouldThrowException_WhenPuntoDoesNotExist() {
        when(puntoRepo.existsById(1L)).thenReturn(false);
        assertThrows(PuntoNotFoundException.class, () -> costoService.obtenerRutasDesdePunto(1L));
        verify(costoRepo, never()).findAllByPunto(anyLong());
    }
}
