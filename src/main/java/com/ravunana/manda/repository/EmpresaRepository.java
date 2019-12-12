package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Empresa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Empresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {

    @Query("select empresa from Empresa empresa where empresa.utilizador.login = ?#{principal.username}")
    List<Empresa> findByUtilizadorIsCurrentUser();

}
