package movielibrary.springboot.restserver;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The {@code BadRequestAdvice} class handles {@link BadRequestException}.
 */
@ControllerAdvice
public class BadRequestAdvice {

  /**
   * The {@code BadRequestAdvice} class handles {@link BadRequestException}
   * thrown by the application.
   * HTTP response with a BAD_REQUEST status. Inidicates that the request is invalid.
   *
   * @param ex The {@code BadRequestException} that was thrown
   * @return A {@code String} containing the error message from the exception
   */
  @ResponseBody
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleBadRequestException(BadRequestException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("message", ex.getMessage());
    return response;
  }
}
