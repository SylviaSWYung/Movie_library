package movielibrary.springboot.restserver;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;

import movielibrary.core.Movie;
import movielibrary.json.internal.MovieDeserializer;
import movielibrary.json.internal.MovieManager;
import movielibrary.json.internal.MovieSerializer;
@Service
public class MovieLibraryService {

  private MovieManager movieManager;
  private MovieDeserializer movieDeserializer;
  private MovieSerializer movieSerializer;

  public MovieLibraryService() throws IOException{
    File jsonFile = new File(
        System.getProperty("user.home")
        + System.getProperty("file.separator")
        + "movies.json"
    );
    this.movieManager = new MovieManager();
    this.movieDeserializer = new MovieDeserializer(jsonFile);
    this.movieSerializer = new MovieSerializer(jsonFile);
  }

  public List<Movie> getMovies() throws IOException {
    movieDeserializer.reloadMovieData();
    return movieDeserializer.getMoviesInLibrary();
  }

  public Movie findMovie(String title) throws IOException{
    return movieDeserializer.findMovie(title);
  }

  public void lendMovie(String title) throws IOException{
    movieManager.lend(title);
  }

  public void returnMovie(String title) throws IOException{
    movieManager.returnBack(title);
  }

  public boolean getLentStatus(String title) throws IOException{
    return movieSerializer.getLentStatus(title);
  }

  public void addMovie(String title, double movieLength, String description) throws IOException{
    movieManager.addMovie(title, movieLength, description);
  }

  public void deleteMovie(String title) throws IOException{
    movieManager.deleteMovie(title);
  }


}
