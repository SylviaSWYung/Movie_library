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

  /**
   * Adds new movie to movielibrary.
   *
   * @param movie new movie that is added to the movielibrary
   * @throws IOException if an I/O error occurs while writing to the file. 
   */
  public void addMovieToLibrary(Movie movie) throws IOException {
    movieDeserializer.getMoviesInLibrary().add(movie);
    writeAllMoviesPretty();
  }

  /**
   * Deletes a movie element from the movielibrary.
   *
   * @param title title of the movie to be deleted
   * @throws IOException if an I/O error occurs while writing to the file or reading file.
   * @throws IllegalStateException if the movie is not present in movielibrary
   * @throws IllegalStateException if there is only 1 movie left in the library
   */
  public void deleteMovieFromLibrary(String title) throws IOException {
    Movie movieToDelete = movieDeserializer.findMovie(title);

    if (movieToDelete == null) {
      throw new IllegalStateException("The movie doesn't exist in the library. ");
    }

    if (movieDeserializer.getMoviesInLibrary().size() <= 1) {
      throw new 
            IllegalStateException("There has to be at least 1 movie left in the movielibrary. ");
    }

    movieDeserializer.getMoviesInLibrary().remove(movieToDelete);
    writeAllMoviesPretty();
  }

  /**
   * Updates the lent status of the movie with the specified title in the library, 
   * then serializes the updated movie list back to the file in a formatted JSON structure. 
   *
   * @param title the title of the movie to update
   * @param newStatus the new lent status to set for the movie
   * @throws IOException if an I/O error occurs while the writing to the file 
   */
  public void changeLentStatus(String title, boolean newStatus) throws IOException {
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
    movieDeserializer.reloadMovieData();
    return this.movieDeserializer.checkIfLent(title);
  }

  /**
   * Finds out if the movie title already exists.
   * The title is not case sensitive and spaces in between characters are 
   * not considered the same movie.
   *
   * @param title the title of the new movie
   * @return a boolean if the movie already exists
   * @throws IOException if an I/O error occurs while reading the file
   */
  public boolean movieIsFound(String title) throws IOException {
    movieDeserializer.reloadMovieData();
    return movieDeserializer.findMovie(title.toLowerCase().strip()) != null;
  }
}
