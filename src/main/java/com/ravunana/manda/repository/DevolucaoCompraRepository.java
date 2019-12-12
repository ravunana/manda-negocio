package com.ravunana.manda.repository;
import com.ravunana.manda.domain.DevolucaoCompra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DevolucaoCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevolucaoCompraRepository extends JpaRepository<DevolucaoCompra, Long>, JpaSpecificationExecutor<DevolucaoCompra> {

}
