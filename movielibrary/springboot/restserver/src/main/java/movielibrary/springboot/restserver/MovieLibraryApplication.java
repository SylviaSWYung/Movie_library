package movielibrary.springboot.restserver;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Spring application.
 * This class initializes the movie data file and runs the Spring Boot application.
 */
@SpringBootApplication
public class MovieLibraryApplication {

  /**
   * Initializes the movie file by copying a default movie JSON file
   * from the project resources to the user's home directory if it does not exist.
   *
   * @throws IOException If there is an error while creating or copying the movie file
   * @throws URISyntaxException If there is an error in resolving the file path. 
   */
  public static void initializeMovieFile() throws IOException, URISyntaxException {
      String userHome = System.getProperty("user.home");
      String fileName = "movies.json";
      File file = new File(userHome, fileName);
      File sourceFile = new File("../../core/src/main/resources/movielibrary/json/internal/movies.json");
      
      // If the movie file doesn't exist, create it and copy the default movie data from resources.
      if (!file.exists()) {
        try {
          if (file.createNewFile()) {
            Files.copy(sourceFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
          }
        } catch(IOException e) {
          e.printStackTrace();
        }
      }
    }

    /**
     * The main method to run the Spring Boot application. 
     * It first initializes the movie file and then starts the Spring Boot application. 
     *
     * @param args Command-line arguments passed to the application.
     * @throws IOException If there is an error initializing the movie file.
     * @throws URISyntaxException If there is an error in resolving the file path for the movie file.
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
      initializeMovieFile();
      SpringApplication.run(MovieLibraryApplication.class, args);
  }
}
