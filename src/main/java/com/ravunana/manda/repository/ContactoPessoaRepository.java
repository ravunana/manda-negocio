package com.ravunana.manda.repository;
import com.ravunana.manda.domain.ContactoPessoa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactoPessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactoPessoaRepository extends JpaRepository<ContactoPessoa, Long> {

}
