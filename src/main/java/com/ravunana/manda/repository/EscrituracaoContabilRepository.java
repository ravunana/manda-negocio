package com.ravunana.manda.repository;
import com.ravunana.manda.domain.EscrituracaoContabil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EscrituracaoContabil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscrituracaoContabilRepository extends JpaRepository<EscrituracaoContabil, Long>, JpaSpecificationExecutor<EscrituracaoContabil> {

    @Query("select escrituracaoContabil from EscrituracaoContabil escrituracaoContabil where escrituracaoContabil.utilizador.login = ?#{principal.username}")
    List<EscrituracaoContabil> findByUtilizadorIsCurrentUser();

}
