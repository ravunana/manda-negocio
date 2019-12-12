package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Compra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Compra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>, JpaSpecificationExecutor<Compra> {

    @Query("select compra from Compra compra where compra.utilizador.login = ?#{principal.username}")
    List<Compra> findByUtilizadorIsCurrentUser();

}
