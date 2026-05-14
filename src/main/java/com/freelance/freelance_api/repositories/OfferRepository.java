package com.freelance.freelance_api.repositories;

import com.freelance.freelance_api.entities.Offer;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Optional<Offer> findByAuthorId(Long authorId);
    Optional<Offer> findByTitle (String title);
}
