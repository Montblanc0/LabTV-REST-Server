package it.labtv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.labtv.model.Film;
import it.labtv.model.Similar;
import it.labtv.model.Trailer;
import it.labtv.service.OneTimeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/onetime")
public class OneTimeController {

    @Autowired
    private OneTimeService service;

    public OneTimeController() {
    }


    @GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getEveryId() {
        return service.getEveryId();
    }

    @PostMapping(value = "/post-all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean insertFilms(@RequestBody Iterable<Film> movies) {
        System.out.println("received: " + ((ArrayList<Film>) movies).size() + " movies");
        movies.forEach(movie -> {
            System.out.println("processing: " + movie);
            movie = this.service.insertFilm(movie);
            System.out.println("PERSISTED: " + movie);
        });
        return true;
    }

    @PostMapping(value = "/post-all-trailers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean insertTrailers(@RequestBody Iterable<Trailer> trailers) {
        System.out.println("received: " + ((ArrayList<Trailer>) trailers).size() + " trailers");
        trailers.forEach(trailer -> {
            System.out.println("processing: " + trailer);
            trailer = this.service.insertTrailer(trailer);
            System.out.println("PERSISTED: " + trailer);
        });
        return true;
    }

    /*/////////////////////////*/
    /*//***TESTING METHODS***//*/
    /*/////////////////////////*/
    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Film insertFilm(@RequestBody Film movie) {
        System.out.println("processing: " + movie);
        movie = this.service.insertFilm(movie);
        return movie;
    }

    @PostMapping(value = "/test-similar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Similar testSimilar(@RequestBody Similar similar) {
        return similar;
    }

    @GetMapping(value = "/test/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Film testMovie(@PathVariable String id) {
        Film movie = this.service.testMovie(id);
        System.out.println("test Film: " + movie);
        return movie;
    }

    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Film test(@RequestBody Film movie) {
        System.out.println("test: " + movie);
        movie = this.service.test(movie);
        return movie;
    }

    @PostMapping(value = "/trailer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Trailer getTrailer(@PathVariable String id) {
        return service.testGetTrailer(id);
    }
}
