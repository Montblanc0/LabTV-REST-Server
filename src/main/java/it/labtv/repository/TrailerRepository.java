package it.labtv.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.labtv.model.Trailer;
import org.springframework.data.repository.query.Param;

public interface TrailerRepository extends CrudRepository<Trailer, Integer>{

    @Query(value = "SELECT * FROM `trailer` WHERE `p_id` = :imdbId", nativeQuery = true)
    Trailer findByImdbId(@Param("imdbId") String imdbId);

}
