package movielibrary.springboot.restserver;

public class MovieNotFoundException extends RuntimeException {

  MovieNotFoundException(String title) {
    super("Could not find movie with title: " + title);
  }
}
