package com.ravunana.manda.repository;
import com.ravunana.manda.domain.FormaLiquidacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FormaLiquidacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormaLiquidacaoRepository extends JpaRepository<FormaLiquidacao, Long>, JpaSpecificationExecutor<FormaLiquidacao> {

}
