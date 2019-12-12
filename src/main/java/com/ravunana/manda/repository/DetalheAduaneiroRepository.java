package com.ravunana.manda.repository;
import com.ravunana.manda.domain.DetalheAduaneiro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetalheAduaneiro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalheAduaneiroRepository extends JpaRepository<DetalheAduaneiro, Long> {

}
