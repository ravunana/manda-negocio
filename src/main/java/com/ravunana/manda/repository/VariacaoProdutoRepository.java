package com.ravunana.manda.repository;
import com.ravunana.manda.domain.VariacaoProduto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VariacaoProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariacaoProdutoRepository extends JpaRepository<VariacaoProduto, Long> {

}
