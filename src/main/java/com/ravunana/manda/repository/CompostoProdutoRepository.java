package com.ravunana.manda.repository;
import com.ravunana.manda.domain.CompostoProduto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompostoProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompostoProdutoRepository extends JpaRepository<CompostoProduto, Long>, JpaSpecificationExecutor<CompostoProduto> {

}
