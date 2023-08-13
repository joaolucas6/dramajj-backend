package com.joaolucas.dramaJJ.repositories;

import com.joaolucas.dramaJJ.models.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
}
