package movielibrary.springboot.restserver;

import java.io.IOException;
import java.util.List;
import movielibrary.core.Movie;
import movielibrary.json.internal.MovieDeserializer;
import movielibrary.json.internal.MovieManager;
import movielibrary.json.internal.MovieSerializer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

/**
 * The {@code MovieLibraryController} class handles the HTTP requests between
 * the server and the application.
 * The HTTP in question are GET, POST, PUT and DELETE requests.
 * The class uses the {@link MovieManager}, {@link MovieDeserializer}
 * and {@link MovieSerializer} classes.
 * to handle the movies in the library.
 */
@RestController
@RequestMapping("movielibrary/movies")
public class MovieLibraryController {

  private final MovieLibraryService movieLibraryService;

  /**
   * Constructor that creates {@link MovieManager}, {@link MovieDeserializer}
   * and {@link MovieSerializer} objects
   * for later handling of the movies in the HTTP requests.
   * The constructor accesses the {@code movies.json} file in the user's home directory.
   *
   * @throws IOException Throws IOException if an I/O error occurs while reading the file.
   */
  public MovieLibraryController(MovieLibraryService movieLibraryService) throws IOException {
    this.movieLibraryService = movieLibraryService;
  }


  /**
   * GET request to get all movies in the {@link movies.json} file.
   * The try clause calls the {@code movieDeserializer.reloadMovieData()} method
   * to make sure the library updated dataand answers with a list of all {@link Movie} objects.
   *
   * @return Returns a list of all movies
   * @throws IOException Throws IOException if an I/O error occurs while reading the file.
   * @throws BadRequestException Throws BadRequestException if
   *          the request fails when the movies cannot be found.
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
   * GET request to find a movie with given title in the {@link movies.json} file.
   * Answers the http get request with a {@Movie} object if the movie isfound.
   * If the movie is not found it throws a BookNotFoundException.
   *
   * @param title The title of the desired movie
   * @return Returns a {@link Movie} object with the given title
   * @throws IOException Throws IOException if an I/O error occurs while reading the file.
   * @throws MovieNotFoundException Throws MovieNotFoundException if the movie cannot be found
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
   * POST request to lend a movie with the given title.
   * Will change the lent status of the movie to be true.
   *
   * @param title The title of the movie we want to lend
   * @throws IOException Throws IOException if an I/O error occurs while reading the file.
   * @throws MovieNotFoundException Throws MovieNotFoundException if the movie cannot be found.
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
   * Handles a POST request to return a previously lent movie by its title.
   * Will change the lent status of the movie to be false.
   *
   * @param title The title of the movie to be returned.
   * @throws IOException Throws IOException if an I/O error occurs while reading the file.
   * @throws MovieNotFoundException Throws MovieNotFoundException if the movie cannot be found.
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
   * @param title The title of the movie.
   * @return {@code true} if the movie is lent, {@code false} otherwise.
   * @throws IOException Throws IOException if an I/O error occurs while reading the file.
   * @throws MovieNotFoundException Throws MovieNotFoundException if the movie cannot be found.
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
   * The movie gets added to the library with the {@code addMovie} method.
   * If the movie cannot be added, a BadRequestException is thrown.
   *
   * @param title The title of the movie.
   * @param movieLength The length of the movie in minutes.
   * @param description A short description of the movie.
   * @throws IOException Throws IOException if an I/O error occurs while reading the file.
   * @throws BadRequestException Throws BadRequestException if the movie cannot be added.
   */
  @PutMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addMovie(@RequestBody Movie newMovie) throws IOException {
    try {
      movieLibraryService.addMovie(newMovie.getTitle(), newMovie.getMovieLength(), newMovie.getDescription());
    } catch (IllegalStateException e) {
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      throw new BadRequestException("Could not add movie");
    }
  }

  /**
   * Handles a DELETE request to remove a movie from the library by its title.
   * Deletes the movie with the {@code deleteMovie} method.
   * If the movie cannot be deleted, a BadRequestException is thrown.
   *
   * @param title The title of the movie to be deleted.
   * @throws IOException Throws IOException if an I/O error occurs while reading the file.
   * @throws BadRequestException Throws BadRequestException if the movie cannot be deleted.
   * @throws MovieNotFoundException Throws MovieNotFoundException if the movie cannot be found.
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
