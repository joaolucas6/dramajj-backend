package com.joaolucas.dramaJJ.repositories;

import com.joaolucas.dramaJJ.models.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
