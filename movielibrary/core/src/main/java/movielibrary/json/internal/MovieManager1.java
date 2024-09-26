package movielibrary.json.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.core.type.TypeReference;

import movielibrary.core.Movie;

public class MovieManager1 {
    
    private File file;
    private Serializer movieSerializer;
    private Deserializer movieDeserializer;

    public MovieManager1() throws IOException {
        this.file = new File("movielibrary/core/src/main/resources/movielibrary/Movies.json");
        movieSerializer = new Serializer(this.file);
        movieDeserializer = new Deserializer();
    }

    /**
     * Returns the file
     * 
     * @return a File object with data for all movies in library
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Sets the file
     * The file cannot be empty and has to be of type File
     * 
     * @param file a File object with a path
     */
    public void setFile(File file) {
        this.file = file;
    }

    /* public Movie findMovie(String title) throws IOException {
        Movie foundMovie = moviesInLibrary.stream()
                                        .filter(m -> m.getTitle().equals(title)).findFirst().orElse(null);

        return foundMovie;
    } */

    /* public boolean checkIfMovieExists(String title) throws IOException {
        return (findMovie(title) != null);
    } */

    public void rent(String title) throws IOException {

        if (this.movieDeserializer.checkIfRented(title)) {
            throw new IllegalStateException("The movie is already rented. ");
        }

        this.movieSerializer.serialize(this.movieDeserializer.findMovie(title), true);
    }

}
