package com.ravunana.manda.repository;
import com.ravunana.manda.domain.LicencaSoftware;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LicencaSoftware entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicencaSoftwareRepository extends JpaRepository<LicencaSoftware, Long> {

}
