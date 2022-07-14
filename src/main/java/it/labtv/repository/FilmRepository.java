package it.labtv.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.labtv.model.Film;

public interface FilmRepository extends CrudRepository<Film, String> {
	@Query(value = "SELECT * FROM `film` WHERE `p_id` LIKE :imdbId", nativeQuery = true)
	Optional<Film> findById(@Param("imdbId") String imdbId);

	@Query("FROM Film f WHERE f.title LIKE %:title%")
	Optional<Set<Film>> findByTitleLike(@Param(value = "title") String title);


}
