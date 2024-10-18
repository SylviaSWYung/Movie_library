package movielibrary.springboot.restserver;

public class BadRequestException extends RuntimeException {
  
  public BadRequestException(String message) {
    super(message);
  }
  
}
