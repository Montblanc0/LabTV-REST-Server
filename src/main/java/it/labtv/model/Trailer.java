package it.labtv.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity
@Table(name = "trailer", indexes = @Index(columnList = "p_id", unique = true))
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"imDbId", "videoId", "videoUrl"})
public class Trailer implements Serializable {
    private static final long serialVersionUID = 1748170640294168944L;
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRAILER", columnDefinition = "INT(10) UNSIGNED")
    private Integer idTrailer;

    @Column(name = "video_url", length = 100)
    private String videoUrl;
    //	@OneToOne(fetch = FetchType.LAZY)
    @OneToOne
    @JsonProperty(value = "imDbId")
    @JoinColumn(name = "p_id", nullable = false)
//	@JsonBackReference(value="trailer")
    private Evidenza evidenza;

    @Transient
    private String videoId;

    public Trailer() {
    }

    public Trailer(Integer idTrailer) {
        this.idTrailer = idTrailer;
    }

    public Integer getIdTrailer() {
        return idTrailer;
    }

    public void setIdTrailer(Integer idTrailer) {
        this.idTrailer = idTrailer;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    @JsonGetter(value = "videoId")
    public String JsonVideoId() {
        if (null == this.videoUrl) return videoId;
        if (!this.videoUrl.isBlank()) this.videoId = videoUrl.split("https://www.youtube.com/watch\\?v=")[1];
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Evidenza getEvidenza() {
        return evidenza;
    }

    public void setEvidenza(Evidenza evidenza) {
        this.evidenza = evidenza;
    }

    @JsonGetter(value = "imDbId")
    public String getEvidenzaId() {
        return evidenza.getId();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Trailer [idTrailer=" + idTrailer + (null != this.evidenza ? ", p_id=" + evidenza.getId() : "") + ", videoUrl=" + videoUrl + "]";
    }


}
