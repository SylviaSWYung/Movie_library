package movielibrary.json.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
        movieLibrary = new ObjectMapper();
        this.file = file;
        //moviesInLibrary = movieLibrary.readValue(this.file, new TypeReference<List<Movie>>() {});
        this.movieDeserializer = new Deserializer(this.file);
        this.moviesInLibrary = movieDeserializer.getMoviesInLibrary();

    }

    //Inspired by https://www.baeldung.com/jackson-object-mapper-tutorial

    public void writeAllMoviesPretty() throws IOException {
        movieLibrary.writerWithDefaultPrettyPrinter().writeValue(this.file, this.moviesInLibrary);
    }

    /* public void serialize(Movie movie) throws IOException {
        moviesInLibrary.add(movie);
        writeAllMoviesPretty();
    } */

    public void serialize(String title, boolean newStatus) throws IOException {
        Movie movieToUpdate = this.movieDeserializer.findMovie(title);
        movieToUpdate.setRented(newStatus);
        writeAllMoviesPretty();
    }

}
