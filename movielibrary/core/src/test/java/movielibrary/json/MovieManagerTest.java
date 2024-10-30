package movielibrary.json;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import movielibrary.json.internal.MovieManager;
import movielibrary.json.internal.MovieSerializer;

//Tests for MovieManager.java class
public class MovieManagerTest {
    
  //MovieManager object to test, File object to use a temporary file
  private MovieManager movieManager;
  private MovieSerializer movieSerializer;
  private File temporaryFile;

  //Default setup for each test
  //Creates a temporary file (a copy of the original moviesTest.json) for the testing, and initializes the MovieManager object
  @BeforeEach
  public void setup() throws IOException {
    File sourceOfFile = new File("../core/src/main/resources/movielibrary/json/internal/moviesTest.json");
    temporaryFile = new File("../core/src/main/resources/movielibrary/json/internal/tempmovies.json");
    Files.copy(sourceOfFile.toPath(), temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    try {
        movieManager = MovieManager.createMovieManager(temporaryFile);
        movieSerializer = new MovieSerializer(temporaryFile);
    } catch (IOException e) {
        fail("An error occured during setup: " + e.getMessage());
    }
  }

  //Deletes the temporary file after each test
  @AfterEach
  public void deleteTemporaryFile() {
    temporaryFile.delete();
  }

  //Tests fake file input in instructor
  @Test
  @DisplayName("Test invalid file input in constructor")
  public void testInvalidFileInput() throws IOException {
    MovieManager fakeMovieManager = MovieManager.createMovieManager(new File("FakeFile.json"));
    File file = new File(
        System.getProperty("user.home") 
        + System.getProperty("file.separator")
        + "movies.json"
    );
    Assertions.assertEquals(file, fakeMovieManager.getFile());

    fakeMovieManager = MovieManager.createMovieManager(null);
    Assertions.assertEquals(file, fakeMovieManager.getFile());
  }

  //Test getFile method of MovieManager.java
  @Test
  @DisplayName("Test file")
  public void testFile() {
    Assertions.assertEquals(temporaryFile, movieManager.getFile());
  }

  //Test setFile method
  @Test
  @DisplayName("Test setFile")
  public void testSetFile() throws IOException {
    File testFile = new File("../core/src/main/resources/movielibrary/newMovies.json");
    movieManager.setFile(testFile);
    Assertions.assertEquals(testFile, movieManager.getFile());
    testFile.delete();
  }

  //Test lend method
  @Test
  @DisplayName("Lend movie")
  public void testLend() throws IOException {
    movieManager.lend("The Trollgirl");
    //Cannot lend when already lent
    Assertions.assertThrows(IllegalStateException.class, () -> {
        movieManager.lend("The Trollgirl");
    });
  }

  //Test ReturnBack method of MovieManager.java
  @Test
  @DisplayName("Return movie")
  public void testReturnBack() throws IOException {
    //Cannot return a book without it being lent
    Assertions.assertThrows(IllegalStateException.class, () -> {
        movieManager.returnBack("Loverboy");
    });
  }

  //Test successful return of a lent movie
  @Test
  @DisplayName("Test successful return")
  public void testSuccessfulReturn() throws IOException {
    movieManager.lend("Day in the life of gr2403");
    movieManager.returnBack("Day in the life of gr2403");
  }

  // Test add movie on the AddMoviePage.fxml
  @Test
  @DisplayName("Test add movie")
  public void testAddMovie() throws IOException {
    // Adds a test movie
    movieManager.addMovie("TestMovie", 10, "Description with more that 20 characters.");

    // Throws IllegalStateException if the movie title already exists
    Assertions.assertThrows(IllegalStateException.class, () -> {
      movieManager.addMovie("Loverboy", 10, "This is a movie about a boy who marries his crush.");
    });
    
    // Check if the test movie has been added to the library
    Assertions.assertTrue(movieSerializer.movieIsFound("TestMovie"), "Movie should be added to the collection.");
  }

  // Test delete movie on the front page
  @Test
  @DisplayName("Test delete movie")
  public void testDeleteMovie() throws IOException {
    // Adds a test movie and deletes the test movie
    movieManager.addMovie("TestDeleteMovie", 10, "This is a test to see if the movie can be deleted.");
    movieManager.deleteMovie("TestDeleteMovie");

    // Checks if the movie has been deleted from the library
    Assertions.assertFalse(movieSerializer.movieIsFound("TestDeleteMovie"), "Movie should be deleted from the library.");

    // Throws a IllegalStateException if the movie does not exist
    Assertions.assertThrows(IllegalStateException.class, () -> {
      movieManager.deleteMovie("Non Existing Movie");;
    });

    // Throws IllegalStateException if there is less that one movie in the library
    Assertions.assertThrows(IllegalStateException.class, () -> {
      movieManager.deleteMovie("Loverboy");
      movieManager.deleteMovie("The Trollgirl");
      movieManager.deleteMovie("Day in the life of gr2403");
      movieManager.deleteMovie("Life is tough");
    });
  }
}


