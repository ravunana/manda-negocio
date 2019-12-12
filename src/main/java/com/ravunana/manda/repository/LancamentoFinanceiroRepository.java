package com.ravunana.manda.repository;
import com.ravunana.manda.domain.LancamentoFinanceiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the LancamentoFinanceiro entity.
 */
@Repository
public interface LancamentoFinanceiroRepository extends JpaRepository<LancamentoFinanceiro, Long>, JpaSpecificationExecutor<LancamentoFinanceiro> {

    @Query("select lancamentoFinanceiro from LancamentoFinanceiro lancamentoFinanceiro where lancamentoFinanceiro.utilizador.login = ?#{principal.username}")
    List<LancamentoFinanceiro> findByUtilizadorIsCurrentUser();

    @Query(value = "select distinct lancamentoFinanceiro from LancamentoFinanceiro lancamentoFinanceiro left join fetch lancamentoFinanceiro.impostos",
        countQuery = "select count(distinct lancamentoFinanceiro) from LancamentoFinanceiro lancamentoFinanceiro")
    Page<LancamentoFinanceiro> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct lancamentoFinanceiro from LancamentoFinanceiro lancamentoFinanceiro left join fetch lancamentoFinanceiro.impostos")
    List<LancamentoFinanceiro> findAllWithEagerRelationships();

    @Query("select lancamentoFinanceiro from LancamentoFinanceiro lancamentoFinanceiro left join fetch lancamentoFinanceiro.impostos where lancamentoFinanceiro.id =:id")
    Optional<LancamentoFinanceiro> findOneWithEagerRelationships(@Param("id") Long id);

}
