package com.ravunana.manda.repository;
import com.ravunana.manda.domain.AuditoriaVenda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AuditoriaVenda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditoriaVendaRepository extends JpaRepository<AuditoriaVenda, Long> {

    @Query("select auditoriaVenda from AuditoriaVenda auditoriaVenda where auditoriaVenda.utilizador.login = ?#{principal.username}")
    List<AuditoriaVenda> findByUtilizadorIsCurrentUser();

}
