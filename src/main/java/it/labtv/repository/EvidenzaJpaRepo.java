package it.labtv.repository;

import it.labtv.model.Evidenza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenzaJpaRepo extends JpaRepository<Evidenza, String> {
    Evidenza getReferenceById(String id);
}