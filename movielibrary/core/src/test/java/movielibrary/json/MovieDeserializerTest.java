package movielibrary.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import movielibrary.core.Movie;
import movielibrary.json.internal.MovieDeserializer;

//Tests for MovieDeserializer.java
public class MovieDeserializerTest {
    
  //MovieDeserializer object to test, File object to use a temporary file
  MovieDeserializer movieDeserializer;
  File temporaryFile;

  //Default setup for each test
  //Creates a temporary file (a copy of the original moviesTest.json) for the testing, and initializes the MovieDeserializer object
  @BeforeEach
  public void setUp() throws IOException{
    File sourceOfFile = new File("../core/src/main/resources/movielibrary/json/internal/moviesTest.json");
    temporaryFile = new File("../core/src/main/resources/movielibrary/json/internal/tempmovies.json");
    Files.copy(sourceOfFile.toPath(), temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

    movieDeserializer = new MovieDeserializer(temporaryFile);
  }

  //Deletes the temporary file after each test
  @AfterEach
  public void deleteTemporaryFile() {
    temporaryFile.delete();
  }

  //Test deserialization process
  @Test
  @DisplayName("Test deserialization process, ensure that json string correctly deserializes into a list of movie")
  public void testDeserializeMovie() throws IOException{
    List<Movie> movies = movieDeserializer.getMoviesInLibrary();
    assertNotNull(movies, "The list of movies should not be null.");
    assertEquals(4, movies.size(), "There should be 4 movies in the library.");
  }

  //Test findMovie method
  @Test
  @DisplayName("Test movie search, test for findMovie()")
  public void testFindMovie() throws IOException{
    Movie movie = movieDeserializer.findMovie("The Trollgirl");
    assertNotNull(movie, "The movie should be found.");
    assertEquals("The Trollgirl", movie.getTitle(), "The title should match.");

  }

  //Test non-existen movie in library
  @Test
  @DisplayName("Test finding a non-exsistent movie")
  public void testFindNoneExistentMovie() throws IOException{
    Movie movie = movieDeserializer.findMovie("NonExistent");
    assertNull(movie, "Non-existent movie should return null");
  }

  //Test checkIfLent method
  @Test
  @DisplayName("Test movie lending status, checkifLent()")
  public void testCheckIfMovieIsLent() throws IOException{
    assertFalse(movieDeserializer.checkIfLent("The Trollgirl"), "The movie should be available to lent");
  }

  //Test non-existent movie is lent
  @Test
  @DisplayName("Test if non-existent movie is lent")
  public void testCheckIfLentNonExistentMovie() throws IOException{
    assertThrows(NoSuchElementException.class, () -> {
        movieDeserializer.checkIfLent("NonExistentMovie");
    }, "Should throw NoSuchElementException for non-exist movie");
  }

  //Test reloadMovieData method
  @Test
  @DisplayName("Test reloading movie data, reloadmoviedata()")
  public void testReloadMovieData() throws IOException{
    movieDeserializer.reloadMovieData();
    List<Movie> movies = movieDeserializer.getMoviesInLibrary();
    assertEquals(4, movies.size(), "Movie library should still contain 4 movie after reloading");
  }

}
