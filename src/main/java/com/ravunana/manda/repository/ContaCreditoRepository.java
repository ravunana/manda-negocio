package com.ravunana.manda.repository;
import com.ravunana.manda.domain.ContaCredito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContaCredito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContaCreditoRepository extends JpaRepository<ContaCredito, Long>, JpaSpecificationExecutor<ContaCredito> {

}
