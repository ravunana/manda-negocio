package com.ravunana.manda.repository;
import com.ravunana.manda.domain.SerieDocumento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SerieDocumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SerieDocumentoRepository extends JpaRepository<SerieDocumento, Long> {

}
