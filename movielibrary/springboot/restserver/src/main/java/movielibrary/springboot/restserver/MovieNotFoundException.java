package movielibrary.springboot.restserver;

/**
 * MovieNotFoundException thrown when a requested movie cannot be found.
 * <p>
 * This exception extends {@link RuntimeException}, allowing it to be thrown
 * at runtime without the need for explicit declaration in method signatures.
 * </p>
 * <p>
 * The exception message includes the title of the movie that could not be found,
 * providing context for the error.
 * </p>
 *
 * @param title The title of the movie.
 */
public class MovieNotFoundException extends RuntimeException {

  MovieNotFoundException(String title) {
    super("Could not find movie with title: " + title);
  }
}
