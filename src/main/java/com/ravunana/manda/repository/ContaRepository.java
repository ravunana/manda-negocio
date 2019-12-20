package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Conta entity.
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>, JpaSpecificationExecutor<Conta> {

    @Query(value = "select distinct conta from Conta conta left join fetch conta.empresas",
        countQuery = "select count(distinct conta) from Conta conta")
    Page<Conta> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct conta from Conta conta left join fetch conta.empresas")
    List<Conta> findAllWithEagerRelationships();

    @Query("select conta from Conta conta left join fetch conta.empresas where conta.id =:id")
    Optional<Conta> findOneWithEagerRelationships(@Param("id") Long id);

    List<Conta> findByContaAgregadora(Conta contaAgregadora);

    Conta findByCodigo(String codigo);

}
