package com.ravunana.manda.repository;
import com.ravunana.manda.domain.ItemVenda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemVenda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long>, JpaSpecificationExecutor<ItemVenda> {

}
