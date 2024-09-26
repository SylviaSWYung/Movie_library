package movielibrary.json.internal;

import java.io.File;
import java.io.IOException;

public class MovieManager {
    
    private File file;
    private MovieSerializer movieSerializer;
    private MovieDeserializer movieDeserializer;

    public MovieManager() throws IOException {
        this.file = new File("movielibrary/core/src/main/resources/movielibrary/Movies.json");
        movieSerializer = new MovieSerializer(this.file);
        movieDeserializer = new MovieDeserializer(this.file);
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

    public void rent(String title) throws IOException {

        if (this.movieDeserializer.checkIfRented(title)) {
            throw new IllegalStateException("The movie is already rented. ");
        }

        this.movieSerializer.serialize(title, true);
    }

    public void returnBack(String title) throws IOException {
        if (!this.movieDeserializer.checkIfRented(title)) {
            throw new IllegalStateException("The movie is not rented. ");
        }

        this.movieSerializer.serialize(title, false);
    }

}
