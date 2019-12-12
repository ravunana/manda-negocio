package com.ravunana.manda.repository;
import com.ravunana.manda.domain.MeioLiquidacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MeioLiquidacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeioLiquidacaoRepository extends JpaRepository<MeioLiquidacao, Long>, JpaSpecificationExecutor<MeioLiquidacao> {

}
