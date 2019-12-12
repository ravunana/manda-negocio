package com.ravunana.manda.repository;
import com.ravunana.manda.domain.LookupItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LookupItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LookupItemRepository extends JpaRepository<LookupItem, Long>, JpaSpecificationExecutor<LookupItem> {

}
