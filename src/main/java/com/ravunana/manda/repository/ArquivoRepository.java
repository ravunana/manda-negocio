package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Arquivo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Arquivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long>, JpaSpecificationExecutor<Arquivo> {

    @Query("select arquivo from Arquivo arquivo where arquivo.utilizador.login = ?#{principal.username}")
    List<Arquivo> findByUtilizadorIsCurrentUser();

}
