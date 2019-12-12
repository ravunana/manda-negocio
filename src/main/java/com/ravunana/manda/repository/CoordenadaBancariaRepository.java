package com.ravunana.manda.repository;
import com.ravunana.manda.domain.CoordenadaBancaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CoordenadaBancaria entity.
 */
@Repository
public interface CoordenadaBancariaRepository extends JpaRepository<CoordenadaBancaria, Long>, JpaSpecificationExecutor<CoordenadaBancaria> {

    @Query(value = "select distinct coordenadaBancaria from CoordenadaBancaria coordenadaBancaria left join fetch coordenadaBancaria.empresas",
        countQuery = "select count(distinct coordenadaBancaria) from CoordenadaBancaria coordenadaBancaria")
    Page<CoordenadaBancaria> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct coordenadaBancaria from CoordenadaBancaria coordenadaBancaria left join fetch coordenadaBancaria.empresas")
    List<CoordenadaBancaria> findAllWithEagerRelationships();

    @Query("select coordenadaBancaria from CoordenadaBancaria coordenadaBancaria left join fetch coordenadaBancaria.empresas where coordenadaBancaria.id =:id")
    Optional<CoordenadaBancaria> findOneWithEagerRelationships(@Param("id") Long id);

}
