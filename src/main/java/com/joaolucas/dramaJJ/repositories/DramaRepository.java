package com.joaolucas.dramaJJ.repositories;

import com.joaolucas.dramaJJ.models.entities.Drama;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DramaRepository extends JpaRepository<Drama, Long> {
}
