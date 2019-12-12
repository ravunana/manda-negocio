package com.ravunana.manda.repository;
import com.ravunana.manda.domain.ClasseConta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClasseConta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasseContaRepository extends JpaRepository<ClasseConta, Long> {

}
