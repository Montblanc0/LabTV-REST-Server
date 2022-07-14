package it.labtv.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@DynamicUpdate
@Table(name = "evidenza", indexes = @Index(columnList = "title"))
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "rank", "rankUpDown", "title", "fullTitle", "year", "image", "crew", "imDbRating",
        "imDbRatingCount"})
public class Evidenza implements Serializable {
    private static final long serialVersionUID = -3821754688513719143L;
    @Id
//	@GeneratedValue(generator = "uuid")
//	@GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 100)
    private String id;
    @Column
    private Double rank;
    @Column
    private Double rankUpDown;
    @Column(length = 100)
    private String title;
    @Column(length = 100)
    private String fullTitle;
    @Column
    private Double year;
    @Column(length = 200)
    private String image;
    @Column(length = 100)
    private String crew;
    @Column(length = 100)
    private String imDbRating;
    @Column(length = 100)
    private String imDbRatingCount;

    //	@JsonManagedReference
//  @JoinColumn(name="id", insertable = false, updatable = false)
    @OneToOne(mappedBy = "evidenza", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Film film;

    //	@JsonManagedReference(value="trailer")
//  @JoinColumn(name="id", insertable = false, updatable = false)
    @OneToOne(mappedBy = "evidenza", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Trailer trailer;

//	@JsonIgnore
//  @JoinColumn(name="id", insertable = false, updatable = false)
//	@OneToOne(mappedBy = "evidenza", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@NotFound(action=NotFoundAction.IGNORE)
//	@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "id", scope = Similar.class)
//	private Similar similar;
//	@OneToMany(mappedBy = "evidenza", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonManagedReference(value = "similars")
//	private List<Similar> similars = new ArrayList<>();
//	@JsonBackReference(value = "similar")
//	private Set<Similar> similars;
//
//	@JsonIgnore
//	public List<Similar> getSimilar() {
//		return similars;
//	}
//
//	public void setSimilars(List<Similar> similars) {
//		this.similars = similars;
//	}

    public Evidenza() {
    }

    public Evidenza(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getRank() {
        return rank;
    }

    @JsonGetter(value = "rank")
    public String getIntRank() {
        if (null == rank)
            return null;
        return String.valueOf(rank.intValue());
    }

    public void setRank(Double rank) {
        this.rank = rank;
    }

    public Double getRankUpDown() {
        return rankUpDown;
    }

    @JsonGetter(value = "rankUpDown")
    public String getIntRankUpDown() {
        if (null == rankUpDown)
            return null;
        return String.valueOf(rankUpDown.intValue());
    }

    public void setRankUpDown(Double rankUpDown) {
        this.rankUpDown = rankUpDown;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public Double getYear() {
        return year;
    }

    @JsonGetter(value = "year")
    public String getIntYear() {
        if (null == year)
            return null;
        return String.valueOf(year.intValue());
    }

    public void setYear(Double year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCrew() {
        return crew;
    }

    @JsonGetter(value = "crew")
    public String getCutCrew() {
        if (null == crew)
            return null;
        if (crew.charAt(0) == '"' && crew.charAt(crew.length() - 1) == '"')
            return crew.substring(1, crew.length() - 1);
        else
            return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public String getImDbRatingCount() {
        return imDbRatingCount;
    }

    public void setImDbRatingCount(String imDbRatingCount) {
        this.imDbRatingCount = imDbRatingCount;
    }

    @JsonIgnore
    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @JsonIgnore
    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }


//  @JsonIgnore
//  public Set<Similar> getSimilars() {
//      return similars;
//  }
//
//  public void setSimilars(Set<Similar> similars) {
//      this.similars = similars;
//  }
//
//	@JsonIgnore
//	public Similar getSimilar() {
//		return similar;
//	}
//
//	public void setSimilar(Similar similar) {
//		this.similar = similar;
//	}

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    @Override
    public String toString() {
        return "Evidenza [id=" + id + ", rank=" + rank + ", rankUpDown=" + rankUpDown + ", title=" + title
                + ", fullTitle=" + fullTitle + ", year=" + year + ", image=" + image + ", crew=" + crew
                + ", imDbRating=" + imDbRating + ", imDbRatingCount=" + imDbRatingCount + ", film=" + film;
//				+ ", trailer=" + trailer + ", similar=" + similar + "]";
//				+ ", trailer=" + trailer + ", similars=" + similars + "]";
    }

}
