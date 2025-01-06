package com.ejercicionivelador.ejercicionivelador.repositoy;

import com.ejercicionivelador.ejercicionivelador.model.Costo;
import com.ejercicionivelador.ejercicionivelador.model.Punto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CostoRepo extends JpaRepository<Costo, Long> {

    @Query("SELECT COUNT(c) > 0 FROM Costo c WHERE c.ida = :ida AND c.vuelta = :vuelta")
    boolean existsByIdaAndVuelta(@Param("ida") Punto ida, @Param("vuelta") Punto vuelta);

    @Modifying
    @Transactional
    @Query("DELETE FROM Costo c WHERE c.ida = :ida AND c.vuelta = :vuelta")
    void deleteByIdaAndVuelta(@Param("ida") Punto ida, @Param("vuelta") Punto vuelta);

    @Query("SELECT c FROM Costo c WHERE (c.ida.id = :ida AND c.vuelta.id = :vuelta) OR (c.ida.id = :vuelta AND c.vuelta.id = :ida)")
    Optional<Costo> findByIdaAndVuelta(@Param("ida") Long ida, @Param("vuelta") Long vuelta);

    @Query("SELECT c FROM Costo c WHERE c.ida.id = :puntoId OR c.vuelta.id = :puntoId")
    List<Costo> findAllByPunto(@Param("puntoId") Long puntoId);

}