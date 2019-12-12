package com.ravunana.manda.repository;
import com.ravunana.manda.domain.DocumentoComercial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentoComercial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoComercialRepository extends JpaRepository<DocumentoComercial, Long>, JpaSpecificationExecutor<DocumentoComercial> {

}
