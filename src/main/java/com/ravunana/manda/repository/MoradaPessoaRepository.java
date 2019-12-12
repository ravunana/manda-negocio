package com.ravunana.manda.repository;
import com.ravunana.manda.domain.MoradaPessoa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MoradaPessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoradaPessoaRepository extends JpaRepository<MoradaPessoa, Long> {

}
