package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Venda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Venda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, JpaSpecificationExecutor<Venda> {

    @Query("select venda from Venda venda where venda.vendedor.login = ?#{principal.username}")
    List<Venda> findByVendedorIsCurrentUser();

}
