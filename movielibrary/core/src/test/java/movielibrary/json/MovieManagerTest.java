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

//Tests for MovieManager.java class
public class MovieManagerTest {
    
  //MovieManager object to test, File object to use a temporary file
  private MovieManager movieManager;
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
    } catch (IOException e) {
        fail("An error occured during setup: " + e.getMessage());
    }
  }

  //Deletes the temporary file after each test
  @AfterEach
  public void deleteTemporaryFile() {
    temporaryFile.delete();
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
}
