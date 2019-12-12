package com.ravunana.manda.repository;
import com.ravunana.manda.domain.RetencaoFonte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RetencaoFonte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RetencaoFonteRepository extends JpaRepository<RetencaoFonte, Long> {

}
