package movielibrary.json.internal;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import movielibrary.core.Movie;

/**
 * The {@code MovieManager} class provides methods to manage the lending and returning of movies in 
 * a movie library. It interacts with the {@link MovieSerializer} class
 * to update and retrieve movie data from a JSON file. 
 */
public class MovieManager {
    
  private File file;
  private MovieSerializer movieSerializer;

  /**
   * Construcs a {@code MovieManager} with a default file path to the movie library data 
   * stored in the user's home directory. If the file does not exist, it initialize the file
   * by copying the default movie data from classpath. The {@code MovieManager}'s
   * file is then set to the new {@code File} object.  
   * Additionally, it initializes the serializer for handling movie data. 
   *
   * <p>The movie data file is located in the user's home directory under the name "movies.json".
   *
   * @throws IOException if an I/O error occurs while reading or writing the file
   */
  public MovieManager() throws IOException {
    File file = new File(
        System.getProperty("user.home") 
        + System.getProperty("file.separator")
        + "movies.json"
    );
    try {
      initializeMovieFile(file);
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    this.file = file;
    movieSerializer = new MovieSerializer(file);
  }

  /**
   * Construcs a {@code MovieManager} with the given file. 
   * Additionally, it initializes the serializer for handling movie data. 
   *
   * @param file a {@code File} object representing the new movie library data file
   * @throws IOException if an I/O error occurs while reading or writing the file
   */
  public MovieManager(File file) throws IOException {
    this.file = file;
    this.movieSerializer = new MovieSerializer(file);
  }

  /**
   * Creates and returns a {@code MovieManager} object.
   * If the file is not null or it exists, the method returns a MovieManager object 
   * using the second constructor. If the file is null or doesn't exist, the method
   * returns a MovieManager object using the first constructor without a parameter. 
   *
   * @param file a {@code File} object representing the new movie library data file
   * @return a {@code MovieManager} object
   * @throws IOException if an I/O error occurs while reading or writing the file
   */
  public static MovieManager createMovieManager(File file) throws IOException {
    MovieManager movieManager;
    
    if (file != null && file.exists()) {
      movieManager = new MovieManager(file);
    } else {
      movieManager = new MovieManager();
    }

    return movieManager;
  }

  /**
   * Initializes the movie file by checking if it exists in the specified location.
   * If it does not exist, a default movie file from the classpath is 
   * copied to the user's home directory. 
   *
   * @param file the file to check and initialize if necessary
   * @throws IOException if an I/O error occurs during the file copying
   * @throws URISyntaxException if there is an issue with the URI of the default movie file resource
   */
  private void initializeMovieFile(File file) throws IOException, URISyntaxException {
    if (!file.exists()) {
      Path originalFile = Path.of(getClass().getResource("movies.json").toURI());
      Files.copy(originalFile, file.toPath());
    }
  }

  /**
   * Returns the file object representing the movie library data.
   *
   * @return a {@code File} object pointing to the movie library JSON file
   */
  public File getFile() {
    return this.file;
  }

  /**
   * Sets the file for the movie library data.
   * The file cannot be empty and has to be of type {@code File}
   *
   * @param file a {@code File} object representing the new movie library data file
   * @throws IOException if an I/O error occurs while reading the file 
   */
  public void setFile(File file) throws IOException {
    this.file = file;
    movieSerializer = new MovieSerializer(file);
  }

  /**
   * Lending a movie with the specified title by setting its lending status to be true. 
   * If the movie is already lent, an {@link IllegalStateException} is thrown.
   *
   * @param title the title of the movie to be lend
   * @throws IOException if an I/O error occurs while accessing the file
   * @throws IllegalStateException if the movie is already lent
   */
  public void lend(String title) throws IOException {
    if (this.movieSerializer.getLentStatus(title)) {
      throw new IllegalStateException("The movie is already lent.");
    }

    this.movieSerializer.changeLentStatus(title, true);
  }

  /**
   * Returns a movie with the specified title by setting its lending status to false. 
   * If the movie is not currently lent, an {@link IllegalStateException} is thrown.
   *
   * @param title the title of the movie to be returned
   * @throws IOException if an I/O error occurs while accessing the file 
   * @throws IllegalStateException if the movie is not currently lent
   */
  public void returnBack(String title) throws IOException {
    if (!this.movieSerializer.getLentStatus(title)) {
      throw new IllegalStateException("The movie is not lent.");
    }

    this.movieSerializer.changeLentStatus(title, false);
  }

  /**
   * Creates and adds a new movie to movielibrary.
   *
   * @param title title of the new movie
   * @param movieLength movielength of the new movie
   * @param description description og the new movie
   * @throws IOException if an I/O error occurs while accessing the file 
   */
  public void addMovie(String title, double movieLength, String description) throws IOException {
    if (movieSerializer.movieIsFound(title)) {
      throw new IllegalStateException("The movie title already exists in the movielibrary!");
    }
    Movie movie = Movie.createMovie(title, movieLength, description);
    movieSerializer.addMovieToLibrary(movie);
  }

  /**
   * Deletes a movie from the movie library. 
   *
   * @param title title of the movie to be deleted
   * @throws IOException if an I/O error occurs while accessing the file 
   */
  public void deleteMovie(String title) throws IOException {
    this.movieSerializer.deleteMovieFromLibrary(title);
  }

}
