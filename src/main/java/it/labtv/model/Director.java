package it.labtv.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "director", indexes = @Index(columnList = "id", unique = true))
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name"})
public class Director implements Serializable {
	private static final long serialVersionUID = -3471547624364353428L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DIRECTOR", columnDefinition = "INT(10) UNSIGNED")
	@JsonIgnore
	private Integer idDirector;
	@Column(length = 100)
	private String id;
	@Column(length = 100)
	private String name;
	//	@JsonBackReference(value="directorList")
	@ManyToMany(mappedBy = "directorList", fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Film.class)
	private Set<Film> movies = new HashSet<>();


	public Director() {
	}

	public Director(Integer idDirector) {
		super();
		this.idDirector = idDirector;
	}


	public Integer getIdDirector() {
		return idDirector;
	}

	public void setIdDirector(Integer idDirector) {
		this.idDirector = idDirector;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Set<Film> getMovies() {
		return movies;
	}

	public void setMovies(Set<Film> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "Director [idDirector = " + idDirector + ", id=" + id + ", name=" + name + "]";
	}

}
