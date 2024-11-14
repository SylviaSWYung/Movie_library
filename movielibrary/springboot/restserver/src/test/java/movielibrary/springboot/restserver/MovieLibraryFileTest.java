package movielibrary.springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Testclass to test the MovieLibraryApplication class for file initialization
public class MovieLibraryFileTest {
  private Path tempDir;
  private Path movieFilePath;
  private MovieLibraryApplication movieLibraryApplication;

  @BeforeEach
  void setup() throws IOException {
      // Set up a temporary directory and modify the user.home system property
      tempDir = Files.createTempDirectory("tempUserHome");
      System.setProperty("user.home", tempDir.toString());
      
      // Define the expected path for the movie file in the temp directory
      movieFilePath = tempDir.resolve("movies.json");
  }

  @SuppressWarnings("static-access")
  @Test
  public void testInitializeMovieFileCreatesFile() throws IOException, URISyntaxException {
      // Ensure the file does not exist before the test
      Files.deleteIfExists(movieFilePath);
      
      // Call the method
      movieLibraryApplication.initializeMovieFile();
      
      // Verify the file has been created
      assertTrue(Files.exists(movieFilePath), "The movies.json file should have been created in the user's home directory.");
  }

  @AfterEach
  void cleanup() throws IOException {
      // Cleanup the temporary directory after each test
      Files.deleteIfExists(movieFilePath);
      Files.deleteIfExists(tempDir);
  }
}
