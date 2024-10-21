package movielibrary.json.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.NoSuchElementException;
import movielibrary.core.Movie;

/**
 * The {@code MovieDeserializer} class provides functionality to deserialize JSON data
 * from a file into a list of {@link Movie} objects. It allows searching for movies by title, 
 * retrieving the list of movies, and checking whether a movie is lent. 
 *
 * <p>This class uses the Jackson library to handle JSON deserialization.</p>
 */ 
public class MovieDeserializer {

  private ObjectMapper movieLibrary;
  private File file; 
  private List<Movie> moviesInLibrary;
    
  /**
   * Constructs a {@code MovieDeserializer} object that initializes the movie file 
   * and reads the movie data into a list. 
   * If the file does not exist, it will be created by copying 
   * the default movie data from the classpath. 
   *
   * @param file the file containing the JSON data of the movie library
   * @throws IOException if an I/O error occurs during the file initialization or reading
   */
  public MovieDeserializer(File file) throws IOException {
    movieLibrary = new ObjectMapper();
    this.file = file;
    try {
      initializeMovieFile(file);
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    try {
      moviesInLibrary = movieLibrary.readValue(this.file, new TypeReference<List<Movie>>(){});
    } catch (IOException e) {
      System.err.println("An error occured during reading movie data: " + e.getMessage());
    }
  }

  /**
   * Initializes the movie file by checking if it exists. 
   * If it does not, a default movie file is copied from the classpath to the specific location. 
   *
   * @param file the file to check and initialize if necessary 
   * @throws IOException if an I/O error occurs during the file creation
   * @throws URISyntaxException if there is an issue with the URI of 
   *                            the default movie file resource.
   */
  private void initializeMovieFile(File file) throws IOException, URISyntaxException {
    if (!file.exists()) {
      InputStream originalFile = getClass().getResourceAsStream("movies.json");
      Files.copy(originalFile, file.toPath());
    }
  }

  /**
   * Finds and returns a movie with the specified title from the deserialized movie library. 
   *
   * @param title the title of the movie to find
   * @return the {@link Movie} object with the specified title, or {@code null} if no movie is found
   * @throws IOException IOException if an I/O error occurs while accessing the movie library
   */
  public Movie findMovie(String title) throws IOException {
    Movie foundMovie = moviesInLibrary.stream()
                                    .filter(selectedMovie -> 
                                    selectedMovie.getTitle().toLowerCase().trim()
                                    .equals(title.toLowerCase().trim()))
                                    .findFirst()
                                    .orElse(null);
    return foundMovie;
  }

  /**
   * Returns the list of all movies in the deserialized movie library. 
   *
   * @return a list of {@link Movie} objects representing the movies in the library
   * @throws IOException if an I/O error occurs while accessing the movie library
   */
  public List<Movie> getMoviesInLibrary() throws IOException {
    return this.moviesInLibrary;
  }

  /**
   * Checks whether the movie with the specified title is currently lent. 
   *
   * @param title the title of the movie to check
   * @return {@code true} if the movie is lent, {@code false} otherwise
   * @throws IOException if an I/O error occurs while the accessing the movie library
   * @throws NoSuchElementException if the movie with the specified 
   *                                title is not found in the library
   */
  public boolean checkIfLent(String title) throws IOException {
    reloadMovieData();
    Movie selectedMovie = findMovie(title);
    if (selectedMovie == null) {
      throw new NoSuchElementException("The movie doesn't exist in the library.");
    }
    return selectedMovie.getIsLent();
  }

  /**
   * Reloads the data from the updated file into moviesInLibrary by 
   * converting the file into a list of {@link Movie}.
   *
   * @throws IOException if an I/O error occurs while reading the file
   */
  public void reloadMovieData() throws IOException {
    moviesInLibrary = movieLibrary.readValue(this.file, new TypeReference<List<Movie>>(){});
  }
    
}
