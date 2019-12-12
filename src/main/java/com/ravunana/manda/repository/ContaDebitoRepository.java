package com.ravunana.manda.repository;
import com.ravunana.manda.domain.ContaDebito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContaDebito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContaDebitoRepository extends JpaRepository<ContaDebito, Long>, JpaSpecificationExecutor<ContaDebito> {

}
