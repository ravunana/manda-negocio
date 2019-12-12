package com.ravunana.manda.repository;
import com.ravunana.manda.domain.EstoqueConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstoqueConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstoqueConfigRepository extends JpaRepository<EstoqueConfig, Long> {

}
