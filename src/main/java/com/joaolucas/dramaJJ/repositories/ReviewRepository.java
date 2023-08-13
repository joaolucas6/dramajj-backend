package com.joaolucas.dramaJJ.repositories;

import com.joaolucas.dramaJJ.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
