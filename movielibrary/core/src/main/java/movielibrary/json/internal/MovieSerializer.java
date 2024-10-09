package movielibrary.json.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import movielibrary.core.Movie;

/**
 * The {@code MovieSerializer} class provides functionality for serializing and 
 * updating a list of {@link Movie} object to a JSON file. It supports updating the lent status
 * and writing the list of movies back to the file. 
 * 
 * <p>This class utilizes the Jackson Library for JSON serialization and deserialization, 
 * and interacts with the {@link MovieDeserializer} class to manage the list of movies. </p>
 */
public class MovieSerializer {

  private ObjectMapper movieLibrary;
  private File file;
  private MovieDeserializer movieDeserializer;

  /**
   * Constructs a {@code MovieSerializer} with the specified file. 
   * Initializes the serializer and loads the current movie data from the file using the 
   * {@link MovieDeserializer} class.
   *
   * @param file the file where the movie data will be read from and written to 
   * @throws IOException if an I/O error occurs while reading the file 
   */
  public MovieSerializer(File file) throws IOException {
    movieLibrary = new ObjectMapper();
    this.file = file;
    this.movieDeserializer = new MovieDeserializer(this.file);

  }

  //Inspired by https://www.baeldung.com/jackson-object-mapper-tutorial
  /**
   * Writes all the movies in the library to the file in a formatted (pretty) JSON structure. 
   * This method overwrites the current file contents with the updated movie list. 
   *
   * @throws IOException if an I/O error occurs while writing to the file. 
   */
  public void writeAllMoviesPretty() throws IOException {
    movieLibrary.writerWithDefaultPrettyPrinter()
                .writeValue(this.file, movieDeserializer.getMoviesInLibrary());
  }

  /* public void serialize(Movie movie) throws IOException {
      moviesInLibrary.add(movie);
      writeAllMoviesPretty();
  } */

  /**
   * Updates the lent status of the movie with the specified title in the library, 
   * then serializes the updated movie list back to the file in a formatted JSON structure. 
   *
   * @param title the title of the movie to update
   * @param newStatus the new lent status to set for the movie
   * @throws IOException if an I/O error occurs while the writing to the file 
   */
  public void serialize(String title, boolean newStatus) throws IOException {
    Movie movieToUpdate = this.movieDeserializer.findMovie(title);
    movieToUpdate.setLent(newStatus);
    writeAllMoviesPretty();
  }

  /**
   * Returns the lent status of the movie with the written title.
   *
   * @param title the title of the movie to get the lent status from
   * @return a boolean with the lent status of the movie
   * @throws IOException if an I/O error occurs while reading the file
   */
  public boolean getLentStatus(String title) throws IOException {
    return this.movieDeserializer.checkIfLent(title);
  }

}
