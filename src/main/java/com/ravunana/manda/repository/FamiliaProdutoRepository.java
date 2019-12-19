package com.ravunana.manda.repository;
import com.ravunana.manda.domain.FamiliaProduto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FamiliaProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamiliaProdutoRepository extends JpaRepository<FamiliaProduto, Long> {

    FamiliaProduto findByHierarquia(FamiliaProduto hierarquia);

}
