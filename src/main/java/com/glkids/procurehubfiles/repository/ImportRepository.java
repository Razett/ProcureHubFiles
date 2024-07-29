package com.glkids.procurehubfiles.repository;

import com.glkids.procurehubfiles.entity.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImportRepository extends JpaRepository<Import, Long> {
    Optional<Import> findByImportno(Long importno);
}
