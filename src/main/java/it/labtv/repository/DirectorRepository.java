package it.labtv.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.labtv.model.Director;

public interface DirectorRepository extends CrudRepository<Director, String>{
	@Query("SELECT new Director (d.idDirector) FROM Director d")
	   Collection<Director> findAllByQuery();
	@Query(value= "SELECT * FROM Director WHERE id = :id", nativeQuery=true)
	Director findByQuery(@Param("id") String id);
}
