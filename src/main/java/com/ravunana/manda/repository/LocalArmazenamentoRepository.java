package com.ravunana.manda.repository;
import com.ravunana.manda.domain.LocalArmazenamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LocalArmazenamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalArmazenamentoRepository extends JpaRepository<LocalArmazenamento, Long> {

}
