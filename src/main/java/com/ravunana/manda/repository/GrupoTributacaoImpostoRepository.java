package com.ravunana.manda.repository;
import com.ravunana.manda.domain.GrupoTributacaoImposto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GrupoTributacaoImposto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoTributacaoImpostoRepository extends JpaRepository<GrupoTributacaoImposto, Long> {

}
