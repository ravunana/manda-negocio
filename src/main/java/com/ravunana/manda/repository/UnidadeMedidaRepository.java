package com.ravunana.manda.repository;
import com.ravunana.manda.domain.UnidadeMedida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnidadeMedida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedida, Long> {

}
