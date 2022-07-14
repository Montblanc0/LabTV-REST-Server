package it.labtv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.labtv.repository.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import it.labtv.model.Evidenza;
import it.labtv.model.Film;
import it.labtv.model.Trailer;

public class OneTimeService {

    @Autowired
    private FilmRepository filmRepo;
    @Autowired
    private TrailerRepository trailerRepo;
    @Autowired
    private EvidenzaRepository evidenzaRepo;
    @Autowired
    private SimilarRepository similarRepo;
    @Autowired
    private DirectorRepository directorRepo;
    @Autowired
    private GenreRepository genreRepo;
    @Autowired
    private StarRepository starRepo;

    @Autowired
    private EvidenzaJpaRepo jpaRepo;

    public OneTimeService() {
    }

    // ***Inserimento della child Entity `Film` tramite setter sulla parent
    // Entity `Evidenza`***
    public Film insertFilm(Film movie) {
        Evidenza evidenza = new Evidenza();
        try {
            evidenza = this.evidenzaRepo.findById(movie.getP_id()).get();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Il record in `evidenza` non esiste, ne creo uno con id=" + movie.getP_id());
            evidenza.setId(movie.getP_id());
            evidenza.setTitle(movie.getTitle());
            evidenza.setImage(movie.getImage());
        }
        // GESTIONE DELLA RELAZIONE CON SIMILAR
        movie = this.checkSimilar(movie);

        // GESTIONE DELLA RELAZIONE CON DIRECTOR
        movie = this.checkDirector(movie);

        // GESTIONE DELLA RELAZIONE CON GENRE
        movie = this.checkGenre(movie);

        // GESTIONE DELLA RELAZIONE CON STAR
        movie = this.checkStar(movie);

        movie.setEvidenza(evidenza);
        evidenza.setFilm(movie);

        System.out.println(" *** PERSISTING ENTITY ***\n" + evidenza);

        movie = this.evidenzaRepo.save(evidenza).getFilm();
        return movie;
    }

    // *** Se ID_SIMILAR non esiste in Evidenza, la crea
    public Film checkSimilar(Film movie) {
        if (null == movie.getSimilarList())
            return movie;

        movie.getSimilarList().forEach(similar -> {
            System.out.println("GESTISCO IL SIMILAR: " + similar);

            if (!evidenzaRepo.existsById(similar.getId())) {
                System.out.println("***EVIDENZA #" + similar.getId() + " non esiste. Inserimento in corso...***");

                Evidenza newEvidenza = new Evidenza(similar.getId());
                newEvidenza.setTitle(similar.getTitle());
                newEvidenza.setImage(similar.getImage());
                newEvidenza.setImDbRating(similar.getImDbRating());

                System.out.println("NUOVA EVIDENZA: " + evidenzaRepo.save(newEvidenza));
            }
        });
        return movie;
    }

    // *** Se il Director esiste, referenzia il suo id
    public Film checkDirector(Film movie) {
        if (null == movie.getDirectorList())
            return movie;

        movie.getDirectorList().forEach(director -> {
            System.out.println("GESTISCO IL DIRECTOR: " + director);

            if (null != directorRepo.findByQuery(director.getId())) {
                director.setIdDirector(directorRepo.findByQuery(director.getId()).getIdDirector());
            }
        });

        return movie;
    }

    // *** Se il Genre esiste, referenzia il suo id
    public Film checkGenre(Film movie) {
        if (null == movie.getGenreList())
            return movie;

        movie.getGenreList().forEach(genre -> {
            System.out.println("GESTISCO IL GENRE: " + genre);

            if (null != genreRepo.findByGenreKey(genre.getGenreKey())) {
                genre.setIdGenre(genreRepo.findByGenreKey(genre.getGenreKey()).getIdGenre());
            }
        });

        return movie;
    }

    // *** Se la Star esiste, referenzia il suo id
    public Film checkStar(Film movie) {
        if (null == movie.getStarList())
            return movie;

        movie.getStarList().forEach(star -> {
            System.out.println("GESTISCO LA STAR: " + star);

            if (null != starRepo.findByStarId(star.getId())) {
                star.setIdStar(starRepo.findByStarId(star.getId()).getIdStar());
            }
        });

        return movie;
    }

    // ***Inserimento della child Entity `Trailer` tramite setter sulla parent
    // Entity `Evidenza`***
    public Trailer insertTrailer(Trailer trailer) {

        System.out.println("GESTISCO IL TRAILER: " + trailer.getEvidenza().getId());

        Trailer existingTrailer = trailerRepo.findByImdbId(trailer.getEvidenza().getId());
        if (null != existingTrailer) {
            System.out.println("Trailer esistente, imposto il suo id...");
            trailer.setIdTrailer(existingTrailer.getIdTrailer());
        }

        Evidenza evidenza = new Evidenza();
        try {
            evidenza = this.jpaRepo.getReferenceById(trailer.getEvidenza().getId());
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Il record in `evidenza` non esiste, ne creo uno con id=" + trailer.getEvidenza().getId());
            evidenza.setId(trailer.getEvidenza().getId());
        }

        trailer.setEvidenza(evidenza);
        evidenza.setTrailer(trailer);

        System.out.println(" *** PERSISTING ENTITY ***\n" + evidenza);

        trailer = this.evidenzaRepo.save(evidenza).getTrailer();
        return trailer;
    }

    // *** Restituisce una lista di ID dei record nella tabella `evidenza`
    public List<String> getEveryId() {
        Set<Evidenza> evidenze = evidenzaRepo.findAllByQuery();
        List<String> ids = new ArrayList<>();
        evidenze.forEach(evidenza -> {
            ids.add(evidenza.getId());
        });
        return ids;
    }

    /*/////////////////////////*/
    /*//***TESTING METHODS***//*/
    /*/////////////////////////*/
    public Film test(Film movie) {
        movie.getSimilarList().forEach(similar -> {
            System.out.println("Controllo Similar #" + similar.getId() + ": "
                    + this.similarRepo.findById(similar.getId()));
        });

        System.out.println(this.checkGenre(movie).getGenreList());
        return movie;
    }

    public Film testMovie(String id) {
        Film movie = this.filmRepo.findById(id).get();
        return movie;
    }

    public Trailer testGetTrailer(String id) {
        return evidenzaRepo.findById(id).get().getTrailer();
    }

}
