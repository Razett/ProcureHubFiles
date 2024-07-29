package com.glkids.procurehubfiles.repository;

import com.glkids.procurehubfiles.entity.ImportFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportFileRepository extends JpaRepository<ImportFile, Long> {
}
