package com.ejercicionivelador.ejercicionivelador.repositoy;

import com.ejercicionivelador.ejercicionivelador.model.Acreditacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcreditacionRepo extends JpaRepository<Acreditacion, Long> {
}