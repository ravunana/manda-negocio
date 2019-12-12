package com.ravunana.manda.repository;
import com.ravunana.manda.domain.RegraMovimentacaoCredito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegraMovimentacaoCredito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegraMovimentacaoCreditoRepository extends JpaRepository<RegraMovimentacaoCredito, Long> {

}
