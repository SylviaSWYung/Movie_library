package movielibrary.json.internal;

import java.io.File;
import java.io.IOException;

/**
 * The {@code MovieManager} class provides methods to manage the renting and returning of movies in 
 * a movie library. It interacts with the {@link MovieSerializer} class
 * to update and retrieve movie data from a JSON file. 
 */
public class MovieManager {
    
    private File file;
    private MovieSerializer movieSerializer;

    /**
     * Construcs a {@code MovieManager} with a default file to path to the movie library data. 
     * Initializes the serializer for handling the movie data. 
     * 
     * @throws IOException if an I/O error occurs while reading the file 
     */
    public MovieManager() throws IOException {
        this.file = new File("../core/src/main/resources/movielibrary/Movies.json");
        movieSerializer = new MovieSerializer(this.file);
    }

    /**
     * Returns the file object representing the movie library data
     * 
     * @return a {@code File} object pointing to the movie library JSON file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Sets the file for the movie library data.
     * The file cannot be empty and has to be of type {@code File}
     * 
     * @param file a {@code File} object representing the new movie library data file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Rents a movie with the specified title by setting its rental status to be true. 
     * If the movie is already rented, an {@link IllegalStateException} is thrown.
     * 
     * @param title the title of the movie to be rented
     * @throws IOException if an I/O error occurs while accessing the file
     * @throws IllegalStateException if the movie is already rented 
     */
    public void rent(String title) throws IOException {

        if (this.movieSerializer.getRentedStatus(title)) {
            throw new IllegalStateException("The movie is already rented.");
        }

        this.movieSerializer.serialize(title, true);
    }

    /**
     * Returns a movie with the specified title by setting its rental status to false. 
     * If the movie is not currently rented, an {@link IllegalStateException} is thrown.
     * 
     * @param title the title of the movie to be returned
     * @throws IOException if an I/O error occurs while accessing the file 
     * @throws IllegalStateException if the movie is not currently rented 
     */
    public void returnBack(String title) throws IOException {
        if (!this.movieSerializer.getRentedStatus(title)) {
            throw new IllegalStateException("The movie is not rented.");
        }

        this.movieSerializer.serialize(title, false);
    }

}
