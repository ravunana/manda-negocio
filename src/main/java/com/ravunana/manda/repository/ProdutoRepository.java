package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Produto entity.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    @Query("select produto from Produto produto where produto.utilizador.login = ?#{principal.username}")
    List<Produto> findByUtilizadorIsCurrentUser();

    @Query(value = "select distinct produto from Produto produto left join fetch produto.impostos left join fetch produto.fornecedors",
        countQuery = "select count(distinct produto) from Produto produto")
    Page<Produto> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct produto from Produto produto left join fetch produto.impostos left join fetch produto.fornecedors")
    List<Produto> findAllWithEagerRelationships();

    @Query("select produto from Produto produto left join fetch produto.impostos left join fetch produto.fornecedors where produto.id =:id")
    Optional<Produto> findOneWithEagerRelationships(@Param("id") Long id);

}
