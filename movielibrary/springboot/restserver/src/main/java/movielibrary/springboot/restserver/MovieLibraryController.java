package movielibrary.springboot.restserver;

import java.io.IOException;
import java.util.List;
import movielibrary.core.Movie;
import movielibrary.json.internal.MovieDeserializer;
import movielibrary.json.internal.MovieManager;
import movielibrary.json.internal.MovieSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code MovieLibraryController} class handles the HTTP requests between
 * the server and the application.
 * The HTTP in question are GET, POST, PUT and DELETE requests.
 *
 * <p>The class relies on {@link MovieManager}, {@link MovieDeserializer},
 * and {@link MovieSerializer} to handle movie data in the library.
 */
@RestController
@RequestMapping("movielibrary/movies")
public class MovieLibraryController {

  private final MovieLibraryService movieLibraryService;

  /**
   * Creates a {@link MovieLibraryController} instance with a {@link MovieLibraryService}.
   *
   * @param movieLibraryService Service for managing movie operations.
   * @throws IOException If an I/O error occurs while initializing the serivce.
   */
  public MovieLibraryController(MovieLibraryService movieLibraryService) throws IOException {
    this.movieLibraryService = movieLibraryService;
  }


  /**
   * Handles a GET request to retrieve all movies in the library.
   *
   * @return A list of all {@link Movie} objects in the library.
   * @throws IOException If an I/O error occurs during retrieval.
   * @throws BadRequestException If movies cannot be retrieved due 
   *                            to an invalid request.
   */
  @GetMapping
  public List<Movie> getMovies() throws IOException {
    try {
      return movieLibraryService.getMovies();
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Could not get the movies in the library");
    }
  }

  /**
   * Handles a GET request to find a movie by title.
   *
   * @param title Title of the desired movie.
   * @return The {@link Movie} object with the given title.
   * @throws IOException If an I/O error occurs during retrieval.
   * @throws MovieNotFoundException If no movie is found with the given title. 
   */
  @GetMapping("/{title}")
  public Movie findMovie(@PathVariable String title) throws IOException {
    try {
      return movieLibraryService.findMovie(title);
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(title);
    }
  }

  /**
   * Handles a POST request to mark a movie as lent.
   * Will change the lent status of the movie to be true.
   *
   * @param title The title of the movie to lend.
   * @throws IOException If an I/O error occurs during the operation.
   * @throws MovieNotFoundException If no movie is found with the given title.
   */
  @PostMapping("/{title}/lend")
  public void lendMovie(@PathVariable String title) throws IOException {
    try {
      movieLibraryService.lendMovie(title);
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(title);
    }
  }

  /**
   * Handles a POST request to mark a movie as returned.
   * Will change the lent status of the movie to be false.
   *
   * @param title Title of the movie to return.
   * @throws IOException If an I/O error occurs during the operation.
   * @throws MovieNotFoundException If the movie cannot be found.
   */
  @PostMapping("/{title}/return")
  public void returnMovie(@PathVariable String title) throws IOException {
    try {
      movieLibraryService.returnMovie(title);
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(title);
    }
  }

  /**
   * Handles a GET request to check whether a movie is currently lent.
   * Returns the lent status of the movie: true if it is lent and false if it is not.
   *
   * @param title The title of the movie to check.
   * @return {@code true} if the movie is lent, {@code false} otherwise.
   * @throws IOException If an I/O error occurs during the operation.
   * @throws MovieNotFoundException If the movie cannot be found.
   */
  @GetMapping("/{title}/lentstatus")
  public boolean getLentStatus(@PathVariable String title) throws IOException {
    try {
      return movieLibraryService.getLentStatus(title);
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(title);
    }
  }


  /**
   * Handles a PUT request to add a new movie to the library.
   *
   * @param newMovie The new {@link Movie} object to be added.
   * @throws IOException If an I/O error occurs during the operation.
   * @throws BadRequestException If the movie cannot be added.
   */
  @PutMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addMovie(@RequestBody Movie newMovie) throws IOException {
    try {
      movieLibraryService.addMovie(newMovie.getTitle(), 
                                  newMovie.getMovieLength(), 
                                  newMovie.getDescription());
    } catch (IllegalStateException e) {
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      throw new BadRequestException("Could not add movie");
    }
  }

  /**
   * Handles a DELETE request to remove a movie from the library by its title.
   *
   * @param title Title of the movie to delete.
   * @throws IOException If an I/O error occurs during the operation.
   * @throws BadRequestException If the movie cannot be deleted.
   * @throws MovieNotFoundException If the movie cannot be found.
   */
  @DeleteMapping("/{title}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteMovie(@PathVariable String title) throws IOException {
    try {
      movieLibraryService.deleteMovie(title);
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(title);
    } catch (Exception e) {
      throw new BadRequestException("Could not delete movie with title: " + title);
    }
  }
}
