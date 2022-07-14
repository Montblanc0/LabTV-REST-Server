package it.labtv.controller;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.labtv.model.Evidenza;
import it.labtv.model.Film;
import it.labtv.model.Trailer;
import it.labtv.repository.EvidenzaRepository;
import it.labtv.repository.FilmRepository;
import it.labtv.repository.TrailerRepository;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:5500"})
@CrossOrigin(origins = "*")
public class MainController {

    public MainController() {
    }

    @Autowired
    private EvidenzaRepository repo;
    @Autowired
    private TrailerRepository trailerRepo;
    @Autowired
    private FilmRepository filmRepo;

    @GetMapping(value = "/evidenze", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Evidenza> getEvidenze() {
        return (HashSet<Evidenza>) repo.getEvidenzeByQuery();
    }

    @GetMapping(value = "films/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Film getFilmById(@PathVariable("id") String id) {
        try {
            return filmRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @GetMapping(value = "films/titoli/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Film> getFilmByTitle(@PathVariable("title") String title) {
        return filmRepo.findByTitleLike(title).get();
    }

    @GetMapping(value = "trailers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Trailer getTrailerById(@PathVariable("id") String id) {
        return trailerRepo.findByImdbId(id);
    }

    /*/////////////////////////*/
    /*//***TESTING METHODS***//*/
    /*/////////////////////////*/
    @GetMapping(value = "/prova", produces = MediaType.APPLICATION_JSON_VALUE)
    public Evidenza prova() {
        return repo.findById("tt0068646").get();
    }

    @GetMapping(value = "/insert")
    public void insert() {
        var trailer = new Trailer();
        trailer.setVideoUrl("http://trailer.com");
        var film = new Film();
        film.setImage("http:/image.com");
        film.setPlot("Plot plot plot plot");
        film.setTitle("Titolo prova");
        var evidenza = new Evidenza("t000001");
        evidenza.setRank(Double.valueOf(4));
        evidenza.setRankUpDown(Double.valueOf(-2));
        evidenza.setTitle(film.getTitle());
        evidenza.setFullTitle("Titolo prova 1 completo full");
        evidenza.setYear(Double.valueOf(2022));
        evidenza.setImage(film.getImage());
        evidenza.setCrew("Attore 1, Attore2");
        evidenza.setImDbRating("0");
        evidenza.setImDbRatingCount("000");
        film.setEvidenza(evidenza);
        trailer.setEvidenza(evidenza);
        evidenza.setFilm(film);
        evidenza.setTrailer(trailer);
        repo.save(evidenza);
        System.out.println("INSERT OK: " + repo.findById("t000001").get());
    }

    @GetMapping(value = "/all-evidenze", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Evidenza> getEvidenze2() {
        return (HashSet<Evidenza>) repo.findAll();
    }


    @GetMapping(value = "evidenza/{id}")
    public Evidenza getEvidenzaById(@PathVariable("id") String id) {
        Evidenza evidenza = repo.findById(id).get();
        return evidenza;
    }

}
