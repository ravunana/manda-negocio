package com.ravunana.manda.repository;
import com.ravunana.manda.domain.EstruturaCalculo;
import com.ravunana.manda.domain.Produto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Spring Data  repository for the EstruturaCalculo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstruturaCalculoRepository extends JpaRepository<EstruturaCalculo, Long> {

    @Query("select estruturaCalculo from EstruturaCalculo estruturaCalculo where estruturaCalculo.utilizador.login = ?#{principal.username}")
    List<EstruturaCalculo> findByUtilizadorIsCurrentUser();

}
