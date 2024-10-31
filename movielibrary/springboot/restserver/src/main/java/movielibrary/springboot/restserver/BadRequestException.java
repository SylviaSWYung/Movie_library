package movielibrary.springboot.restserver;

/**
 * BadRequestException thrown when a requested could not be handled.
 * <p>
 * This exception extends {@link RuntimeException}, allowing it to be thrown
 * at runtime without the need for explicit declaration in method signatures.
 * </p>
 * <p>
 * The exception returns an error message given in the method in {@link MovieLibraryController}.
 * </p>
 *
 * @param title The title of the movie.
 */
public class BadRequestException extends RuntimeException {
  
  public BadRequestException(String message) {
    super(message);
  }
  
}
