package movielibrary.json.internal;

import java.io.File;
import java.io.IOException;
import movielibrary.core.Movie;

/**
 * The {@code MovieManager} class provides methods to manage the lending and returning of movies in 
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
    this.file = new File("../core/src/main/resources/movielibrary/movies.json");
    movieSerializer = new MovieSerializer(this.file);
  }

  /**
   * Returns the file object representing the movie library data.
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
   * @throws IOException if an I/O error occurs while reading the file 
   */
  public void setFile(File file) throws IOException {
    this.file = file;
    movieSerializer = new MovieSerializer(file);
  }

  /**
   * Lending a movie with the specified title by setting its lending status to be true. 
   * If the movie is already lent, an {@link IllegalStateException} is thrown.
   *
   * @param title the title of the movie to be lend
   * @throws IOException if an I/O error occurs while accessing the file
   * @throws IllegalStateException if the movie is already lent
   */
  public void lend(String title) throws IOException {
    if (this.movieSerializer.getLentStatus(title)) {
      throw new IllegalStateException("The movie is already lent.");
    }

    this.movieSerializer.changeLentStatus(title, true);
  }

  /**
   * Returns a movie with the specified title by setting its lending status to false. 
   * If the movie is not currently lent, an {@link IllegalStateException} is thrown.
   *
   * @param title the title of the movie to be returned
   * @throws IOException if an I/O error occurs while accessing the file 
   * @throws IllegalStateException if the movie is not currently lent
   */
  public void returnBack(String title) throws IOException {
    if (!this.movieSerializer.getLentStatus(title)) {
      throw new IllegalStateException("The movie is not lent.");
    }

    this.movieSerializer.changeLentStatus(title, false);
  }

  /**
   * Creates and adds a new movie to movielibrary.
   *
   * @param title title of the new movie
   * @param movieLength movielength of the new movie
   * @param description description og the new movie
   * @throws IOException if an I/O error occurs while accessing the file 
   */
  public void addMovie(String title, double movieLength, String description) throws IOException {
    Movie movie = Movie.createMovie(title, movieLength, description);
    movieSerializer.addMovieToLibrary(movie);
  }

  /**
   * Deletes a movie from the movie library. 
   *
   * @param title title of the movie to be deleted
   * @throws IOException if an I/O error occurs while accessing the file 
   */
  public void deleteMovie(String title) throws IOException {
    this.movieSerializer.deleteMovieFromLibrary(title);
  }

}
