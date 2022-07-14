package it.labtv.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.labtv.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, String> {
    @Query("SELECT new Genre (g.idGenre) FROM Genre g")
    Collection<Genre> findAllByQuery();

    Genre findByGenreKey(String genreKey);
}
