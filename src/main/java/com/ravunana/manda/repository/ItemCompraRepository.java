package com.ravunana.manda.repository;
import com.ravunana.manda.domain.ItemCompra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ItemCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long>, JpaSpecificationExecutor<ItemCompra> {

    @Query("select itemCompra from ItemCompra itemCompra where itemCompra.solicitante.login = ?#{principal.username}")
    List<ItemCompra> findBySolicitanteIsCurrentUser();

}
