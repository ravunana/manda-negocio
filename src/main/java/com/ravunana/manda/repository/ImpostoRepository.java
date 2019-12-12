package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Imposto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Imposto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImpostoRepository extends JpaRepository<Imposto, Long>, JpaSpecificationExecutor<Imposto> {

}
