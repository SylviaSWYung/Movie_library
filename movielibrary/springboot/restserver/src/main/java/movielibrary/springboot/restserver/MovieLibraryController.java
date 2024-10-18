package movielibrary.springboot.restserver;

import movielibrary.core.Movie;
import movielibrary.json.internal.MovieManager;
import movielibrary.json.internal.MovieDeserializer;
import movielibrary.json.internal.MovieSerializer;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The {@code MovieLibraryController} class handles the HTTP requests between the server and the application
 * The HTTP in question are GET, POST, PUT and DELETE requests
 * The class uses the {@link MovieManager}, {@link MovieDeserializer} and {@link MovieSerializer} classes
 * to handle the movies in the library
 */
@RestController
public class MovieLibraryController {

  private MovieManager movieManager;
  private MovieDeserializer movieDeserializer;
  private MovieSerializer movieSerializer;

  /**
   * Constructor that creates {@link MovieManager}, {@link MovieDeserializer} and {@link MovieSerializer} objects
   * for later handling of the movies in the HTTP requests
   * @throws IOException Throws IOException if an I/O error occurs while accessing the inputsteam
   */
  public MovieLibraryController() throws IOException {
    File jsonFile = new File("./movielibrary/core/src/main/resources/movielibrary/movies.json");
    movieManager = new MovieManager();
    movieDeserializer = new MovieDeserializer(jsonFile); // fix so that class has inputstream as input?
    movieSerializer = new MovieSerializer(jsonFile); // fix so that class has inputstream as input?
  }


  /**
   * Get request to get all movies in the {@link movies.json} file. Answers with a list of all {@link Movie} objects
   * @return Returns a list of all movies
   * @throws BadRequestException Throws BadRequestException if the request fails and the 
   */
  // get movies request
  @GetMapping("movielibrary/movies")
  public List<Movie> getMovies() throws IOException {
    try {
      return movieDeserializer.getMoviesInLibrary();
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Could not get the movies in the library");
    }
  }

  // find movie request
  @GetMapping("movielibrary/movies/movie/{title}")
  public Movie findMovie(@PathVariable String title) throws IOException {
    try {
      return movieDeserializer.findMovie(title);
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(title);
    }
  }

  // lend movie request
  @PostMapping("movielibrary/lendmovie/{title}")
  public void lendMovie(@RequestBody Movie movie) throws IOException {
    try {
      movieManager.lend(movie.getTitle());
      movieSerializer.writeAllMoviesPretty();
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(movie.getTitle());
    }
  }

  // return movie request
  @PostMapping("movielibrary/returnmovie/{title}")
  public void returnMovie(@RequestBody Movie movie) throws IOException {
    try {
      movieManager.returnBack(movie.getTitle());
      movieSerializer.writeAllMoviesPretty();
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(movie.getTitle());
    }
  }

  // get lent status request
  @GetMapping("movielibrary/movies/lentstatus/{title}")
  public boolean getLentStatus(@PathVariable String title) throws IOException {
    try {
      return movieSerializer.getLentStatus(title);
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(title);
    }
  }


  // addmovie request
  @PutMapping("movielibrary/movies/addmovie/{title}")
  public void addMovie(@RequestBody Movie movie) throws IOException {
    try {
      movieSerializer.addMovieToLibrary(movie);
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(movie.getTitle());
    } catch (Exception e) {
      throw new BadRequestException("could not add movie with title: " + movie.getTitle());
    }
  }
  
  // deletemovie request
  @DeleteMapping("movielibrary/movies/deletemovie/{title}")
  public void deleteMovie(@PathVariable String title) throws IOException {
    try {
      movieManager.deleteMovie(title);
      movieSerializer.writeAllMoviesPretty();
    } catch (IllegalArgumentException e) {
      throw new MovieNotFoundException(title);
    } catch (Exception e) {
      throw new BadRequestException("could not delete movie with title: " + title);
    }
  }
}
