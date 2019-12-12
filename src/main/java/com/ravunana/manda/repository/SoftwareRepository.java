package com.ravunana.manda.repository;
import com.ravunana.manda.domain.Software;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Software entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long> {

}
