package com.ravunana.manda.repository;
import com.ravunana.manda.domain.RegraMovimentacaoDebito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegraMovimentacaoDebito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegraMovimentacaoDebitoRepository extends JpaRepository<RegraMovimentacaoDebito, Long> {

}
