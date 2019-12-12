package com.ravunana.manda.repository;
import com.ravunana.manda.domain.DevolucaoVenda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DevolucaoVenda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevolucaoVendaRepository extends JpaRepository<DevolucaoVenda, Long>, JpaSpecificationExecutor<DevolucaoVenda> {

}
