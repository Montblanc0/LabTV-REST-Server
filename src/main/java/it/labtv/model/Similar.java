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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "similar", indexes = @Index(columnList = "title"))
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "title", "image", "imDbRating"})
public class Similar implements Serializable {
    private static final long serialVersionUID = 3440413737053070530L;

    @Column(name = "title", length = 100)
    private String title;
    @Column(name = "image", length = 200)
    private String image;
    @Column(name = "imDbRating", length = 100)
    private String imDbRating;

//	@JsonProperty(value="id")
//	@Column(name="p_id_film")
//	private String p_id_film;
//
//	@Id
//	@JsonIgnore
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "ID_SIMILAR")
//	private Integer idSimilar;
//
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
//	@JsonProperty(value="id")
//	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "evidenza")
//	@JoinColumn(name = "id", referencedColumnName = "id")

    @Id
    @Column(name = "ID_SIMILAR", length = 100)
    private String id;

    //	@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "id", scope = Evidenza.class)
//	@JsonIdentityReference(alwaysAsId = true)
//	@OneToOne(cascade= {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
//	@OneToOne
//	@JsonUnwrapped
//	@JsonManagedReference(value = "similar")
//	@JsonBackReference(value = "similars")
//	@JoinColumn(name = "p_id_film", referencedColumnName = "id",  unique = true)
//	@JsonProperty(value = "id")
//	@ManyToOne
//	@JoinColumn(name = "ID_SIMILAR")
//	@MapsId
//	private Evidenza evidenza;
//
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Film.class)
//	@NotFound(action=NotFoundAction.IGNORE)
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "similar_film", joinColumns = {
//			@JoinColumn(name = "p_id_similar", referencedColumnName = "ID_SIMILAR") }, inverseJoinColumns = {
//					@JoinColumn(name = "p_id_film", referencedColumnName = "p_id") })
//	@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "id", scope = Film.class)
    @ManyToMany(mappedBy = "similarList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Film> movies = new HashSet<>();


    public Similar() {
    }

    public Similar(String id) {
        super();
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//	public String getP_id_film() {
//		return p_id_film;
//	}
//
//	public void setP_id_film(String p_id_film) {
//		this.p_id_film = p_id_film;
//	}
//
//	public String getId() {
//		return evidenza.getId();
//	}
//
//	public Integer getId() {
//		return idSimilar;
//	}
//
//	public void setId(Integer idSimilar) {
//		this.idSimilar = idSimilar;
//	}
//
//	@JsonGetter(value="id")
//	public Integer getIdSimilar() {
//		return idSimilar;
//	}
//	@JsonSetter(value="id")
//	public void setIdSimilar(Integer idSimilar) {
//		this.idSimilar = idSimilar;
//	}
//
//	@JsonGetter(value="id")
//	@JsonIgnore
//	public Evidenza getEvidenza() {
//		return evidenza;
//	}
//
////@JsonGetter(value="id")
//	@JsonIgnore
//	public String getEvidenzaId() {
//		return evidenza.getId();
//	}
//
////@JsonSetter(value="id")
//	public void setEvidenza(Evidenza evidenza) {
//		this.evidenza = evidenza;
//	}

    @JsonIgnore
    public Set<Film> getMovies() {
        return movies;
    }

    public void setMovies(Set<Film> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        String toString = "Similar [" + (null != id ? "id=" + id + ", " : "") + "title="
//				String toString = "Similar [" + (null != idSimilar ? "idSimilar=" + idSimilar + ", " : "") + "title="
                + this.getTitle() + ", image=" + this.getImage();
//				+ (null != evidenza ? ", evidenza=" + evidenza.getId() + (null != evidenza.getTitle() ? " - " + evidenza.getTitle() : "") : "");
        if (null != movies)
            for (Film movie : movies) {
                toString += ", Film=" + movie.getP_id() + movie.getTitle() + " ";
            }
        return toString + "]";
    }
}
