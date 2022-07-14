package it.labtv.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "film", indexes = @Index(columnList = "title"))
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "title", "image", "plot", "directorList", "starList", "genreList", "similars"})
public class Film implements Serializable {
	private static final long serialVersionUID = 3183696855915738358L;

	@Id
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@JsonProperty(value = "id")
	@Column(name = "p_id", length = 100)
	private String p_id;
	@Column(length = 200)
	private String image;
	@Column(length = 100)
	private String title;
	@Column(length = 800)
	private String plot;

	//	@JsonManagedReference(value = "starList")
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("id")
	@JoinTable(name = "star_film", joinColumns = {@JoinColumn(name = "p_id_film")}, inverseJoinColumns = {
			@JoinColumn(name = "p_id_star")})
	private Set<Star> starList = new HashSet<>();

	//	@JsonManagedReference(value = "directorList")
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("id")
	@JoinTable(name = "director_film", joinColumns = {@JoinColumn(name = "p_id_film")}, inverseJoinColumns = {
			@JoinColumn(name = "p_id_director")})
	private Set<Director> directorList = new HashSet<>();


	//	@JsonManagedReference(value = "genreList")
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("genreKey")
	@JoinTable(name = "genre_film", joinColumns = {@JoinColumn(name = "p_id_film")}, inverseJoinColumns = {
			@JoinColumn(name = "p_id_genre")})
	private Set<Genre> genreList = new HashSet<>();


	//	@JsonManagedReference(value = "similars")
	@JsonProperty(value = "similars")
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("id")
	@JoinTable(name = "similar_film", joinColumns = {
			@JoinColumn(name = "p_id_film", referencedColumnName = "p_id")}, inverseJoinColumns = {
			@JoinColumn(name = "p_id_similar", referencedColumnName = "ID_SIMILAR")})
	private Set<Similar> similarList = new HashSet<>();

	@OneToOne
	@MapsId
	@JoinColumn(name = "p_id")
	private Evidenza evidenza;

	public Film() {
	}

	public Film(String p_id) {
		this.p_id = p_id;
	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public Set<Director> getDirectorList() {
		return directorList;
	}

	public void setDirectorList(Set<Director> directorList) {
		this.directorList = directorList;
	}

	public Set<Star> getStarList() {
		return starList;
	}

	public void setStarList(Set<Star> starList) {
		this.starList = starList;
	}

	public Set<Genre> getGenreList() {
		return genreList;
	}

	public void setGenreList(Set<Genre> genreList) {
		this.genreList = genreList;
	}

	public Set<Similar> getSimilarList() {
		return similarList;
	}

	public void setSimilarList(Set<Similar> similarList) {
		this.similarList = similarList;
	}

	@JsonIgnore
	public Evidenza getEvidenza() {
		return evidenza;
	}

	public void setEvidenza(Evidenza evidenza) {
		this.evidenza = evidenza;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Film [id=" + p_id + ", image=" + image + ", title=" + title + ", plot=" + plot + ", starList="
				+ starList + ", directorList=" + directorList + ", genreList=" + genreList + ", similarList="
				+ similarList + "]";
	}

}
