package movielibrary.json.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import movielibrary.core.Movie;

/**
 * 
 */
public class Serializer {

    private ObjectMapper movieLibrary;
    private File file;
    private List<Movie> moviesInLibrary;
    private Deserializer movieDeserializer;

    /**
     * Initializes the Serializer object
     */
    public Serializer(File file) throws IOException {
        //movieLibrary = new ObjectMapper();
        this.file = file;
        //moviesInLibrary = movieLibrary.readValue(this.file, new TypeReference<List<Movie>>() {});
        movieDeserializer = new Deserializer(this.file);
        moviesInLibrary = movieDeserializer.getMoviesInLibrary();

    }

    //Inspired by https://www.baeldung.com/jackson-object-mapper-tutorial

    public void writeAllMoviesPretty() throws IOException {
        movieLibrary.writerWithDefaultPrettyPrinter().writeValue(this.file, moviesInLibrary);
    }

    /* public Movie findMovie(String title) throws IOException {
        Movie foundMovie = moviesInLibrary.stream()
                                        .filter(m -> m.getTitle().equals(title)).findFirst().orElse(null);

        return foundMovie;
    } */

    public void serialize(Movie movie) throws IOException {
        /* if (findMovie(movie.getTitle()) != null) {
            throw new IllegalStateException("The movie already exists in the library. ");
        } */

        moviesInLibrary.add(movie);
        writeAllMoviesPretty();
    }

    public void serialize(Movie movieToUpdate, boolean newStatus) throws IOException {
        //Movie movieToUpdate = findMovie(title);

        /* if (findMovie(title) == null) {
            throw new NoSuchElementException("The movie " + title + " is not in the library");
        } */

        movieToUpdate.setRented(newStatus);
        writeAllMoviesPretty();
    }

}
