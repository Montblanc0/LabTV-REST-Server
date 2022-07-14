package it.labtv.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.labtv.model.Star;

public interface StarRepository extends CrudRepository<Star, String>{
	@Query("SELECT new Star (s.idStar) FROM Star s")
	   Collection<Star> findAllByQuery();
	@Query(value= "SELECT * FROM Star WHERE id = :id", nativeQuery=true)
	Star findByStarId(@Param("id") String id);
}
