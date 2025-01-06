package com.ejercicionivelador.ejercicionivelador;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.ejercicionivelador.ejercicionivelador.exceptions.*;
import com.ejercicionivelador.ejercicionivelador.model.Punto;
import com.ejercicionivelador.ejercicionivelador.repositoy.PuntoRepo;
import com.ejercicionivelador.ejercicionivelador.service.PuntoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PuntoServiceTest {

    @Mock
    private PuntoRepo puntoRepo;

    @InjectMocks
    private PuntoService puntoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPuntos_ShouldReturnListOfPuntos_WhenPuntosExist() {
        List<Punto> puntos = List.of(new Punto(1L, "Punto 1"), new Punto(2L, "Punto 2"));
        when(puntoRepo.findAll()).thenReturn(puntos);
        List<Punto> result = puntoService.getPuntos();
        assertEquals(2, result.size());
        assertEquals("Punto 1", result.get(0).getNombre());
        verify(puntoRepo, times(1)).findAll();
    }

    @Test
    void getPuntos_ShouldThrowException_WhenNoPuntosExist() {
        when(puntoRepo.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NoPuntosFoundException.class, () -> puntoService.getPuntos());
        verify(puntoRepo, times(1)).findAll();
    }

    @Test
    void getPuntoById_ShouldReturnPunto_WhenPuntoExists() {
        Punto punto = new Punto(1L, "Punto 1");
        when(puntoRepo.findById(1L)).thenReturn(Optional.of(punto));
        Optional<Punto> result = puntoService.getPuntoById(1L);
        assertTrue(result.isPresent());
        assertEquals("Punto 1", result.get().getNombre());
        verify(puntoRepo, times(1)).findById(1L);
    }

    @Test
    void getPuntoById_ShouldThrowException_WhenPuntoDoesNotExist() {
        when(puntoRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PuntoNotFoundException.class, () -> puntoService.getPuntoById(1L));
        verify(puntoRepo, times(1)).findById(1L);
    }

    @Test
    void addPunto_ShouldSavePunto_WhenPuntoIsValid() {
        Punto punto = new Punto(null, "Nuevo Punto");
        when(puntoRepo.save(punto)).thenReturn(new Punto(1L, "Nuevo Punto"));
        Punto result = puntoService.addPunto(punto);
        assertNotNull(result.getId());
        assertEquals("Nuevo Punto", result.getNombre());
        verify(puntoRepo, times(1)).save(punto);
    }

    @Test
    void addPunto_ShouldThrowException_WhenPuntoAlreadyExists() {
        Punto punto = new Punto(1L, "Punto Existente");
        when(puntoRepo.existsById(1L)).thenReturn(true);
        assertThrows(PuntoAlreadyExistsException.class, () -> puntoService.addPunto(punto));
        verify(puntoRepo, times(1)).existsById(1L);
    }

    @Test
    void updatePunto_ShouldUpdatePunto_WhenPuntoExists() {
        Punto punto = new Punto(null, "Punto Actualizado");
        when(puntoRepo.existsById(1L)).thenReturn(true);
        when(puntoRepo.save(any(Punto.class))).thenReturn(new Punto(1L, "Punto Actualizado"));
        Punto result = puntoService.updatePunto(1L, punto);
        assertEquals(1L, result.getId());
        assertEquals("Punto Actualizado", result.getNombre());
        verify(puntoRepo, times(1)).existsById(1L);
        verify(puntoRepo, times(1)).save(punto);
    }

    @Test
    void updatePunto_ShouldThrowException_WhenPuntoDoesNotExist() {
        Punto punto = new Punto(null, "Punto Inexistente");
        when(puntoRepo.existsById(1L)).thenReturn(false);
        assertThrows(PuntoNotFoundException.class, () -> puntoService.updatePunto(1L, punto));
        verify(puntoRepo, times(1)).existsById(1L);
    }

    @Test
    void deletePunto_ShouldDeletePunto_WhenPuntoExists() {
        when(puntoRepo.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> puntoService.deletePunto(1L));
        verify(puntoRepo, times(1)).existsById(1L);
        verify(puntoRepo, times(1)).deleteById(1L);
    }

    @Test
    void deletePunto_ShouldThrowException_WhenPuntoDoesNotExist() {
        when(puntoRepo.existsById(1L)).thenReturn(false);
        assertThrows(PuntoNotFoundException.class, () -> puntoService.deletePunto(1L));
        verify(puntoRepo, times(1)).existsById(1L);
    }
}

