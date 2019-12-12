package com.ravunana.manda.repository;
import com.ravunana.manda.domain.FluxoDocumento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FluxoDocumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FluxoDocumentoRepository extends JpaRepository<FluxoDocumento, Long>, JpaSpecificationExecutor<FluxoDocumento> {

}
