package movielibrary.springboot.restserver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The {@code MovieNotFoundAdvice} class handles
 * the {@link MovieNotFoundException} thrown by the application.
 * HTTP response with a NOT_FOUND status. Inidicates that the movie could not be found.
 *
 * @param ex The {@code MovieNotFoundException} that was thrown
 * @return A {@code String} containing the error message from the exception
 */
@ControllerAdvice
public class MovieNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(MovieNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String movieNotFoundHandler(MovieNotFoundException ex) {
    return ex.getMessage();
  }
}
