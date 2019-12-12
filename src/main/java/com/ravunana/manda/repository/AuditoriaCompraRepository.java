package com.ravunana.manda.repository;
import com.ravunana.manda.domain.AuditoriaCompra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AuditoriaCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditoriaCompraRepository extends JpaRepository<AuditoriaCompra, Long> {

    @Query("select auditoriaCompra from AuditoriaCompra auditoriaCompra where auditoriaCompra.utilizador.login = ?#{principal.username}")
    List<AuditoriaCompra> findByUtilizadorIsCurrentUser();

}
