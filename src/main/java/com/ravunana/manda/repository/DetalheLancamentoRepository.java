package com.ravunana.manda.repository;
import com.ravunana.manda.domain.DetalheLancamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the DetalheLancamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalheLancamentoRepository extends JpaRepository<DetalheLancamento, Long>, JpaSpecificationExecutor<DetalheLancamento> {

    @Query("select detalheLancamento from DetalheLancamento detalheLancamento where detalheLancamento.utilizador.login = ?#{principal.username}")
    List<DetalheLancamento> findByUtilizadorIsCurrentUser();

}
