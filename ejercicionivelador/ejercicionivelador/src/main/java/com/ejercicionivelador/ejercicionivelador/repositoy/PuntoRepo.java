package com.ejercicionivelador.ejercicionivelador.repositoy;

import com.ejercicionivelador.ejercicionivelador.model.Punto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntoRepo  extends JpaRepository<Punto, Long> {
}
