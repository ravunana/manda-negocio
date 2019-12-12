package com.ravunana.manda.repository;
import com.ravunana.manda.domain.ContactoEmpresa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactoEmpresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactoEmpresaRepository extends JpaRepository<ContactoEmpresa, Long> {

}
