package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Moeda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Moeda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoedaRepository extends JpaRepository<Moeda, Long>, JpaSpecificationExecutor<Moeda> {

}
