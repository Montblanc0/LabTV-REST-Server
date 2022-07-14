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
@Table(name = "star", indexes = @Index(columnList = "id", unique = true))
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name"})
public class Star implements Serializable {

    private static final long serialVersionUID = 6031284237787277741L;
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_STAR", columnDefinition = "INT(10) UNSIGNED")
    private Integer idStar;
    @Column(length = 100)
    private String id;
    @Column(length = 100)
    private String name;
    //	@JsonBackReference(value="starList")
    @ManyToMany(mappedBy = "starList", fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Film.class)
    private Set<Film> movies = new HashSet<>();

    public Star() {
    }

    public Star(Integer idStar) {
        super();
        this.idStar = idStar;
    }

    public Integer getIdStar() {
        return idStar;
    }

    public void setIdStar(Integer idDirector) {
        this.idStar = idDirector;
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
        return "Star [idStar = " + idStar + ", id=" + id + ", name=" + name + "]";
    }

}
