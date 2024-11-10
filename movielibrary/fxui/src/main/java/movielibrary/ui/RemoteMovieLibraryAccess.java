package movielibrary.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import movielibrary.core.Movie;

/**
 * The {@code RemoteMovieLibraryAccess} class is responsible for handling the 
 * communication between the client and the server.
 * It sends requests to the server and receives responses from the server.
 * The class is responsible for fetching, adding, 
 * deleting, lending and returning movies.
 */
public class RemoteMovieLibraryAccess {
  
  private String baseUri = "http://localhost:";

  private static final String APPLICATION_JSON = "application/json";

  private static final String ACCEPT_HEADER = "Accept";

  private static final String CONTENT_TYPE_HEADER = "Content-Type";

  private static final int DEFAULT_PORT = 8080;

  private ObjectMapper objectMapper; 

  /**
   * Initializes the {@code RemoteMovieLibraryAccess} with the default port.
   */
  public RemoteMovieLibraryAccess() {
    this(DEFAULT_PORT);
  }

  /**
   * Initializes the {@code RemoteMovieLibraryAccess} with the specified port.
   *
   * @param port the server port to connect to 
   */
  public RemoteMovieLibraryAccess(final int port) {
    baseUri += port + "/";
    this.objectMapper = new ObjectMapper();  
  }

  /**
   * Retrieves a list of all movies from the remote server.
   *
   * @return a list of Movie objects
   * @throws RuntimeException if there is an error in fetching the movies
   */
  public List<Movie> getMovies() {
    String endpoint = baseUri + "movielibrary/movies";
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(endpoint))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .GET()
          .build();
      HttpResponse<String> response = HttpClient.newBuilder()
                                                .build()
                                                .send(request, HttpResponse
                                                              .BodyHandlers.ofString());
      return objectMapper.readValue(response.body(), new TypeReference<List<Movie>>(){});
    } catch (IOException | InterruptedException e) {
      throw new  RuntimeException("Failed to fetch movies", e);
    }
  }

  /**
   * Retrieves a movie by its title from the remote server.
   *
   * @param title the title of the movie to fetch
   * @return the Movie object with the specified title
   * @throws RuntimeException if there is an error in fetching the movie
   */
  public Movie getMovieByTitle(String title) {
    String endpoint = baseUri + "movielibrary/movies/" + title;
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(endpoint))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .GET()
          .build();
      HttpResponse<String> response = HttpClient
                                          .newBuilder()
                                          .build() 
                                          .send(request, HttpResponse
                                                        .BodyHandlers.ofString());
      return objectMapper.readValue(response.body(), Movie.class);
    } catch (IOException | InterruptedException e) {
      throw new  RuntimeException("Failed to fetch movie with title: " + title, e);
    }
  }

  /**
   * Checks if a movie is currently lent.
   *
   * @param title the title of the movie
   * @return true if the movie is lent, false otherwise
   * @throws RuntimeException if there is an error in fetching the lent status
   */
  public boolean getLentStatus(String title) {
    String endpoint = baseUri + "movielibrary/movies/" + title + "/lentstatus";
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(endpoint))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .GET()
          .build();
      HttpResponse<String> response = HttpClient
                                          .newBuilder()
                                          .build()
                                          .send(request, HttpResponse
                                                        .BodyHandlers.ofString());
      return objectMapper.readValue(response.body(), Boolean.class);
    } catch (IOException | InterruptedException e) {
      throw new  RuntimeException("Failed to fetch lent status for movie with title: " + title, e);
    } 
  }

  /**
   * Lends a movie by its title. 
   *
   * @param title the title of the movie to lend
   * @throws RuntimeException if there is an error in lending the movie
   */
  public void lendMovie(String title) {
    String endpoint = baseUri + "movielibrary/movies/" + title + "/lend";
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(endpoint))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();
      HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding());
    } catch (IOException | InterruptedException e) {
      throw new  RuntimeException("Failed to lend movie with title: " + title, e);
    }
  }

  /**
   * Returns a movie by its title.
   *
   * @param title the title of the movie to return
   * @throws RuntimeException if there is an error in returning the movie
   */
  public void returnMovie(String title) {
    String endpoint = baseUri + "movielibrary/movies/" + title + "/return";
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(endpoint))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();
      HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding());
    } catch (IOException | InterruptedException e) {
      throw new  RuntimeException("Failed to return movie with title: " + title, e);
    }
  }

  /**
   * Adds a new movie to the library.
   *
   * @param movie the Movie object to add
   * @throws RuntimeException if there is an error in adding the movie
   * @throws IllegalStateException if the movie title already exists
   */
  public void addMovie(Movie movie) {
    String endpoint = baseUri + "movielibrary/movies";
    try {
      String jsonBody = objectMapper.writeValueAsString(movie);
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(endpoint))
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
          .build();
      HttpResponse<Void> response = 
                          HttpClient.newBuilder()
                                    .build()
                                    .send(request, HttpResponse.BodyHandlers.discarding());
      if (response.statusCode() == 201) {
        return;
      } else if (response.statusCode() == 409) {
        throw new IllegalStateException("The movie title already exists in the movielibrary!");
      } else if (response.statusCode() != 200) {
        throw new RuntimeException("Failed to add movie; server responded with status: " 
                                    + response.statusCode());
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Failed to add movie; " + movie.getTitle(), e);
    }
  }

  /**
   * Deletes a movie by its title.
   *
   * @param title the title of the movie to delete
   * @throws RuntimeException if there is an error in deleting the movie
   */
  public void deleteMovie(String title) {
    String endpoint = baseUri + "movielibrary/movies/" + title;
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(endpoint))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .DELETE()
          .build();
      HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.discarding());
    } catch (IOException | InterruptedException e) {
      throw new  RuntimeException("Failed to delete movie with title: " + title, e);
    }
  }

}
