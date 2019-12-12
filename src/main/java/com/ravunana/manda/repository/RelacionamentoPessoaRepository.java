package com.ravunana.manda.repository;
import com.ravunana.manda.domain.RelacionamentoPessoa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RelacionamentoPessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelacionamentoPessoaRepository extends JpaRepository<RelacionamentoPessoa, Long> {

}
