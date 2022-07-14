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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "genre", indexes = @Index(columnList = "genre_key", unique = true))
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"key", "value"})
public class Genre implements Serializable {
	private static final long serialVersionUID = 3931792021443705301L;

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_GENRE", columnDefinition = "INT(10) UNSIGNED")
	private Integer idGenre;
	@JsonProperty(value = "key")
	@Column(name = "genre_key", length = 100)
	private String genreKey;
	@JsonProperty(value = "value")
	@Column(name = "genre_value", length = 100)
	private String genreValue;
	//	@JsonBackReference(value="genreList")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Film.class)
	@ManyToMany(mappedBy = "genreList", fetch = FetchType.LAZY)
	private Set<Film> movies = new HashSet<>();


	public Genre() {
	}

	public Genre(Integer idGenre) {
		super();
		this.idGenre = idGenre;
	}

	public Integer getIdGenre() {
		return idGenre;
	}

	public void setIdGenre(Integer idDirector) {
		this.idGenre = idDirector;
	}

	public String getGenreKey() {
		return genreKey;
	}

	public void setGenreKey(String id) {
		this.genreKey = id;
	}

	public String getGenreValue() {
		return genreValue;
	}

	public void setGenreValue(String name) {
		this.genreValue = name;
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
		return "Genre [idGenre = " + idGenre + ", genreKey=" + genreKey + ", genreValue=" + genreValue + "]";
	}
}
