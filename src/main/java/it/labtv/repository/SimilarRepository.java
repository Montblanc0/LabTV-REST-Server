package it.labtv.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.labtv.model.Similar;

public interface SimilarRepository extends CrudRepository<Similar, String> {
	@Query("SELECT new Similar (s.id) FROM Similar s")
	Collection<Similar> findAllByQuery();

	@Query(value = "SELECT * FROM Similar WHERE id = :imdbId", nativeQuery = true)
	Collection<Similar> findAllByImdbId(@Param("imdbId") String imdbId);

	@Query(value = "SELECT * FROM Similar WHERE id = :imdbId", nativeQuery = true)
	Similar findByImdbId(@Param("imdbId") String imdbId);

	Similar saveAndFlush(Similar similar);
}
