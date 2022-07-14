package it.labtv.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.labtv.model.Evidenza;

public interface EvidenzaRepository extends CrudRepository<Evidenza, String> {
    @Query("SELECT new Evidenza (e.id) FROM Evidenza e")
    Set<Evidenza> findAllByQuery();

    @Query(value = "SELECT * FROM Evidenza WHERE year is not null and rank is not null ORDER BY rank", nativeQuery = true)
    Set<Evidenza> getEvidenzeByQuery();

    Evidenza saveAndFlush(Evidenza evidenza);

}
