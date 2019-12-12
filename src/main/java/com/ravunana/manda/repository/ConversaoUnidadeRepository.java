package com.ravunana.manda.repository;
import com.ravunana.manda.domain.ConversaoUnidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConversaoUnidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConversaoUnidadeRepository extends JpaRepository<ConversaoUnidade, Long> {

}
