package movielibrary.springboot.restserver;

import java.io.File;
import java.io.IOException;
import java.util.List;
import movielibrary.core.Movie;
import movielibrary.json.internal.MovieDeserializer;
import movielibrary.json.internal.MovieManager;
import movielibrary.json.internal.MovieSerializer;
import org.springframework.stereotype.Service;

/**
 * The {@code MovieLibraryService} class provides services to manage movies in the library.
 * This includes adding, deleting, lending, returning and checking the lent status of movies.
 *
 * <p>The class uses {@link MovieManager}, {@link MovieDeserializer} and {@link MovieSerializer}
 * for JSON file operations on movies stored in the user's home directory.
 */
@Service
public class MovieLibraryService {

  private MovieManager movieManager;
  private MovieDeserializer movieDeserializer;
  private MovieSerializer movieSerializer;

  /**
   * Initializes the {@code MovieLibraryService} with the file handling for movies
   * stored in the useræs home directory.
   *
   * @throws IOException If an I/O error occurs while accessing or creating the JSON file. 
   */
  public MovieLibraryService() throws IOException {
    File jsonFile = new File(
        System.getProperty("user.home")
        + System.getProperty("file.separator")
        + "movies.json"
    );
    this.movieManager = new MovieManager();
    this.movieDeserializer = new MovieDeserializer(jsonFile);
    this.movieSerializer = new MovieSerializer(jsonFile);
  }

  /**
   * Retrieves a list of all movies in the library.
   *
   * @return A list of all {@link Movie} objects.
   * @throws IOException If an I/O error occurs during data retrieval.
   */
  public List<Movie> getMovies() throws IOException {
    movieDeserializer.reloadMovieData();
    return movieDeserializer.getMoviesInLibrary();
  }

  /**
   * Finds a specific movie by title. 
   *
   * @param title Title of the movie to find. 
   * @return The {@link Movie} object with the specified title.
   * @throws IOException If an I/O error occurs during the search.
   */
  public Movie findMovie(String title) throws IOException {
    return movieDeserializer.findMovie(title);
  }

  /**
   * Marks a movie as lent by changing the lent status to true.
   *
   * @param title Title of the movie to lend.
   * @throws IOException If an I/O error occurs during the operation.
   */
  public void lendMovie(String title) throws IOException {
    movieManager.lend(title);
  }

  /**
   * Marks a movie as returned by changing the lent status to false.
   *
   * @param title Title of the movie to return.
   * @throws IOException If an I/O error occurs during the operation.
   */
  public void returnMovie(String title) throws IOException {
    movieManager.returnBack(title);
  }

  /**
   * Checks the lent status of a movie.
   *
   * @param title Title of the movie to check.
   * @return {@code true} if the movie is lent, {@code false} otherwise.
   * @throws IOException If an I/O error occurs during the operation.
   */
  public boolean getLentStatus(String title) throws IOException {
    return movieSerializer.getLentStatus(title);
  }

  /**
   * Adds a new movie to the library.
   *
   * @param title Title of the movie.
   * @param movieLength Length of the movie in minutes.
   * @param description Short description of the movie.
   * @throws IOException If an I/O error occurs during the operation.
   */
  public void addMovie(String title, double movieLength, String description) throws IOException {
    movieManager.addMovie(title, movieLength, description);
  }

  /**
   * Deletes a movie from the library by title. 
   *
   * @param title Title of the movie to delete.
   * @throws IOException If an I/O error occurs during the operation.
   */
  public void deleteMovie(String title) throws IOException {
    movieManager.deleteMovie(title);
  }


}
