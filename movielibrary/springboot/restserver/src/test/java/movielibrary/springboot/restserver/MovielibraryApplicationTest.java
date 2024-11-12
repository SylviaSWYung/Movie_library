package movielibrary.springboot.restserver;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import movielibrary.core.Movie;


// Testclass to test the logic in MovieLibraryService, MovieLibraryController and MovieLibraryApplication
@SpringBootTest(classes = {MovieLibraryApplication.class, MovieLibraryService.class, MovieLibraryController.class}) 
@AutoConfigureMockMvc
public class MovielibraryApplicationTest {
  
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MovieLibraryService movieLibraryService;

  private Movie testMovie;
  private Movie testMovie2;

  // setUp method to create the default movie objects used in the tests
  @BeforeEach
  void setUp() {
    testMovie = new Movie("Moana", 100, "Moana about a girl who saves her island");
    testMovie2 = new Movie("Frozen", 100, "Movie about a girl with ice powers");
  }

  // Test method to test the getMovies method in MovieLibraryController
  @Test
  public void testGetMovies() throws Exception {
    when(movieLibraryService.getMovies()).thenReturn(List.of(testMovie, testMovie2));
    
    mockMvc.perform(MockMvcRequestBuilders.get("/movielibrary/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(testMovie.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movieLength").value(testMovie.getMovieLength()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(testMovie.getDescription()))
                // Assertions for second movie
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value(testMovie2.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].movieLength").value(testMovie2.getMovieLength()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value(testMovie2.getDescription()));
  }

  // Test method to test that exception is thrown when no movies are found
  @Test
  public void testGetMoviesThrowsException() throws Exception {
    when(movieLibraryService.getMovies()).thenThrow(new IllegalArgumentException("Could not get the movies in the library"));

    mockMvc.perform(MockMvcRequestBuilders.get("/movielibrary/movies"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not get the movies in the library"));
  }

  // Test method to test the findMovie method in MovieLibraryController
  @Test
  public void testFindMovie() throws Exception {
    when(movieLibraryService.findMovie("Moana")).thenReturn(testMovie);

    mockMvc.perform(MockMvcRequestBuilders.get("/movielibrary/movies/Moana"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testMovie.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieLength").value(testMovie.getMovieLength()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testMovie.getDescription()));
  }

  // Test method to test that exception is thrown when the movie is not found
  @Test
  public void testFindMovieThrowsException() throws Exception {
    when(movieLibraryService.findMovie("testMovie")).thenThrow(new IllegalArgumentException("Could not find movie with title: testMovie"));

    mockMvc.perform(MockMvcRequestBuilders.get("/movielibrary/movies/testMovie"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find movie with title: testMovie"));
  }

  // Test method to test the lendMovie method in MovieLibraryController
  @Test
  public void testLendMovie() throws Exception {
    doNothing().when(movieLibraryService).lendMovie("Moana");

    mockMvc.perform(MockMvcRequestBuilders.post("/movielibrary/movies/Moana/lend"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    verify(movieLibraryService).lendMovie("Moana");
  }

  // Test method to test that exception is thrown when the movie is not found when trying to lend it
  @Test
  public void testLendMovieThrowsException() throws Exception {
    doThrow(new IllegalArgumentException("Could not find movie with title: testMovie")).when(movieLibraryService).lendMovie("testMovie");
    mockMvc.perform(MockMvcRequestBuilders.post("/movielibrary/movies/testMovie/lend"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find movie with title: testMovie"));
  }

  // Test method to test the returnMovie method in MovieLibraryController
  @Test
  public void testReturnMovie() throws Exception {
    doNothing().when(movieLibraryService).returnMovie("Moana");

    mockMvc.perform(MockMvcRequestBuilders.post("/movielibrary/movies/Moana/return"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    verify(movieLibraryService).returnMovie("Moana");
  }

  // Test method to test that exception is thrown when the movie is not found when trying to return it
  @Test
  public void testReturnMovieThrowsException() throws Exception {
    doThrow(new IllegalArgumentException("Could not find movie with title: testMovie")).when(movieLibraryService).returnMovie("testMovie");
    mockMvc.perform(MockMvcRequestBuilders.post("/movielibrary/movies/testMovie/return"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find movie with title: testMovie"));
  }

  // Test method to test the getLentStatus method in MovieLibraryController
  @Test
  public void testGetLentStatus() throws Exception {
    when(movieLibraryService.getLentStatus("Moana")).thenReturn(true);

    mockMvc.perform(MockMvcRequestBuilders.get("/movielibrary/movies/Moana/lentstatus"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
  }

  // Test method to test that exception is thrown when the movie is not found when trying to get the lent status
  @Test
  public void testGetLentStatusThrowsException() throws Exception {
    doThrow(new IllegalArgumentException("Could not find movie with title: testMovie")).when(movieLibraryService).getLentStatus("testMovie");
    mockMvc.perform(MockMvcRequestBuilders.get("/movielibrary/movies/testMovie/lentstatus"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find movie with title: testMovie"));
  }

  // Test method to test the addMovie method in MovieLibraryController
  @Test
  public void testAddMovie() throws Exception {
    doNothing().when(movieLibraryService).addMovie("Moana", 100, "Moana about a girl who saves her island");

    mockMvc.perform(MockMvcRequestBuilders.put("/movielibrary/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Moana\", \"movieLength\":100, \"description\":\"Moana about a girl who saves her island\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    verify(movieLibraryService).addMovie("Moana", 100, "Moana about a girl who saves her island");
  }

  // Test method to test that exception is thrown when trying to add it
  @Test
  public void testAddMovieThrowsException() throws Exception {
    doThrow(new IllegalArgumentException("Could not add movie")).when(movieLibraryService).addMovie("testMovie", 100, "testMovie description");
    mockMvc.perform(MockMvcRequestBuilders.put("/movielibrary/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"testMovie\", \"movieLength\":100, \"description\":\"testMovie description\"}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not add movie"));
  }

  // Test method to test the deleteMovie method in MovieLibraryController
  @Test
  public void testDeleteMovie() throws Exception {
    doNothing().when(movieLibraryService).deleteMovie("Moana");

    mockMvc.perform(MockMvcRequestBuilders.delete("/movielibrary/movies/Moana"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    verify(movieLibraryService).deleteMovie("Moana");
  }

  // Test method to test that exception is thrown when the movie is not found when trying to delete it
  @Test
  public void testDeleteMovieThrowsException() throws Exception {
    // Checks for when the movie cannot be found
    doThrow(new IllegalArgumentException("Could not find movie with title: testMovie")).when(movieLibraryService).deleteMovie("testMovie");
    mockMvc.perform(MockMvcRequestBuilders.delete("/movielibrary/movies/testMovie"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not find movie with title: testMovie"));

    // Checks for when the movie cannot be deleted
    doThrow(new RuntimeException()).when(movieLibraryService).deleteMovie("testMovie");
    mockMvc.perform(MockMvcRequestBuilders.delete("/movielibrary/movies/testMovie"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not delete movie with title: testMovie"));
  }
}
