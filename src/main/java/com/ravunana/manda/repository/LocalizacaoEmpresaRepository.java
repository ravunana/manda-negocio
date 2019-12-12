package com.ravunana.manda.repository;
import com.ravunana.manda.domain.LocalizacaoEmpresa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LocalizacaoEmpresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalizacaoEmpresaRepository extends JpaRepository<LocalizacaoEmpresa, Long> {

}
