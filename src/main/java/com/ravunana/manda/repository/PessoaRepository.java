package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Pessoa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Pessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {

    @Query("select pessoa from Pessoa pessoa where pessoa.utilizador.login = ?#{principal.username}")
    List<Pessoa> findByUtilizadorIsCurrentUser();

}
