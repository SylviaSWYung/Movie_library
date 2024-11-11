package movielibrary.ui;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import movielibrary.core.Movie;

public class RemoteMovieLibraryAccessTest {
  
  private WireMockServer wireMockServer;
  private WireMockConfiguration config;

  @Mock
  private RemoteMovieLibraryAccess access;

  // Inspired by "TODO-list-example" on gitlab 
  // https://gitlab.stud.idi.ntnu.no/it1901/todo-list
  @BeforeEach
  public void startWireMockServerAndSetup() throws URISyntaxException {
    config = WireMockConfiguration.wireMockConfig().port(8089);
    wireMockServer = new WireMockServer(config.portNumber());
    wireMockServer.start();
    WireMock.configureFor("localhost", config.portNumber());
    access = new RemoteMovieLibraryAccess(wireMockServer.port());
  }

  @AfterEach
  public void stopWireMockServer() {
    wireMockServer.stop();
  }

  // Test for "getMovies" method, simulating a GET request to retrieve a list of movies
  // and verifying that the returned list contains the correct movie titles.
  @Test
  public void testGetMovies() {
    stubFor(get(urlEqualTo("/movielibrary/movies"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody("[{\"title\":\"Interstellar\",\"movieLength\":100,\"description\":\"Movie about space and time\"}," +
            "{\"title\":\"Inception\",\"movieLength\":120,\"description\":\"Movie about dreams\"}]")));
    
    List<Movie> movies = access.getMovies();
    assertEquals(2, movies.size());
    assertEquals("Interstellar", movies.get(0).getTitle());
    assertEquals("Inception", movies.get(1).getTitle());
  }

  // Test for "getMovieByTitle" method, simulating a GET request to retrieve a movie by title
  // and verifying that the returned movie has the correct details.
  @Test
  public void testGetMovieByTitle() {
    stubFor(get(urlEqualTo("/movielibrary/movies/Interstellar"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody("{\"title\":\"Interstellar\",\"movieLength\":100,\"description\":\"Movie about space and time\"}")));
    
    Movie movie = access.getMovieByTitle("Interstellar");
    assertEquals("Interstellar", movie.getTitle());
  }

  // Test for "getLentStatus" method, simulating a GET request to retrieve the lent status of a movie
  // and verifying that the returned status is correct.
  @Test
  public void testGetLentStatus() {
    stubFor(get(urlEqualTo("/movielibrary/movies/Interstellar/lentstatus"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody("true")));
    
    boolean lent = access.getLentStatus("Interstellar");
    assertEquals(true, lent);
  }

  // Test for "lendMovie" method, simulating a POST request to lend a movie
  // and verifying that the request was made to the currect URL.
  @Test
  public void testLendMovie() {
    stubFor(post(urlEqualTo("/movielibrary/movies/Interstellar/lend"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("true")));
    
    access.lendMovie("Interstellar");
    verify(postRequestedFor(urlEqualTo("/movielibrary/movies/Interstellar/lend")));
  }

  // Test for "returnMovie" method, simulating a POST request to return a movie
  // and verifying that the request was made to the correct URL.
  @Test
  public void testReturnMovie() {
    stubFor(post(urlEqualTo("/movielibrary/movies/Moana/return"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("true")));
    
    access.returnMovie("Moana");
    verify(postRequestedFor(urlEqualTo("/movielibrary/movies/Moana/return")));
  }

  // Test for "addMovie" method, simulating a PUT request to add a movie
  // and verifying that the movie is added with the correct title.
  @Test
  public void testAddMovie() {
    stubFor(put(urlEqualTo("/movielibrary/movies"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"title\":\"Interstellar\",\"movieLength\":100,\"description\":\"Movie about space and time\"}")));
    
    Movie movie = new Movie("Interstellar", 100, "Movie about space and time");
    access.addMovie(movie);
    assertEquals("Interstellar", movie.getTitle());
  }

  // Test for "deleteMovie" method, simulating a DELETE request to delete a movie
  // and verifying that the request was made to the correct URL for deletion.
  @Test
  public void testDeleteMovie() {
    stubFor(delete(urlEqualTo("/movielibrary/movies/Stitch"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("true")));
    
    access.deleteMovie("Interstellar");
    verify(deleteRequestedFor(urlEqualTo("/movielibrary/movies/Interstellar")));
  }

}
