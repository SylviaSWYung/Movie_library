package movielibrary.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import movielibrary.core.Movie;

/**
 * The {@code FrontPageController} handles the FXML file {@code FrontPage} and it's behaviour.
 * Handles the user interaction with the UI components on the FrontPage.
 * The user interaction includes choosing a movie from the {@code ChoiceBox} 
 * and clicking the {@code MoreInfo} button.
 */
public class FrontPageController {
  
  /**
   * Rest API object used for calling backend application.
   */
  private RemoteMovieLibraryAccess access;

  /**
   * FXML UI components on the FrontPage.
   * {@code MoreInfobtn} is a button that redirects the user to the {@code MoviePage.fxml} page
   */
  @FXML
  private Button moreInfobtn;

  /**
   * FXML UI components on the FrontPage.
   * {@code addMoviebtn} is a button that redirects the user to the {@code AddMoviePage.fxml} page.
   */
  @FXML 
  private Button addMoviebtn;

  /**
   * {@code MovieScrollBar} is a {@code ChoiceBox} that contains 
   * all the movie titles from the {@code Movies.json} file.
   */
  @FXML
  private ChoiceBox<String> movieScrollBar;

  /**
   * Initializes the front page by loading the movie titles from the remote movie library access,
   * and populating the {@code movieScrollBar} with the movie titles.
   *
   * @throws IOException If an I/O error occurs while accessing the movies.
   */
  public void initializes(RemoteMovieLibraryAccess access) throws IOException {
    this.access = access;

    try {
      List<Movie> movies = access.getMovies();
      List<String> movieTitles = new ArrayList<>();
      for (Movie mov : movies) {
        movieTitles.add(mov.getTitle());
      }
      movieScrollBar.getItems().addAll(movieTitles);
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles MoreInfobtn OnAction.
   * Throws error if the user has not chosen a movie before clicking {@code MoreInfobtn}
   * If the user has chosen a movie and clicks the {@code MoreInfobtn}, 
   * the {@code FrontPage.fxml} will load to {@code MoviePage.fxml}
   *
    * @throws IOException Throws IOException if an I/O error occurs while accessing the file
    */
  @FXML
  public void handleMoreInfoButton() throws IOException {
    String chosenMovie = movieScrollBar.getValue();
    if (chosenMovie != null && !chosenMovie.isEmpty()) {
      Movie movie = access.getMovieByTitle(chosenMovie);
      loadPage("MoviePage.fxml", movie.getTitle(), movie.getDescription(), movie.getMovieLength());
    } else {
      Alert alert = new Alert(AlertType.ERROR, "Please choose a movie from the scrollbar menu");
      alert.setTitle("Error");
      alert.setHeaderText("No movie chosen");
      alert.showAndWait();
    }
  }

  /**
   * Handles AddMoviebtn onAction. Loads AddMoviePage.fxml when the button is clicked.
   *
   * @throws IOException Throws IOException if an I/O error occurs while accessing the file
   */
  public void handleAddMovieButton() throws IOException {
    try {
      FXMLLoader loader = new FXMLLoader(getClass()
                          .getResource("/movielibrary/ui/AddMoviePage.fxml"));
      Parent parent = loader.load();

      Stage stage = (Stage) addMoviebtn.getScene().getWindow();
      Scene scene = new Scene(parent);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * loadPage method for redirecting and loading the {@code MoviePage.fxml} page.
   *
   * @param page A String with the .fxml file
   * @param movieTitle A String with the movieTitle of the chosen movie
   * @param description A String with the description of the chosen movie
   * @param movieLength A double with the length of the chosen movie
   */
  public void loadPage(String page, String movieTitle, String description, double movieLength) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/movielibrary/ui/" + page));
      Parent parent = loader.load();

      MoviePageController moviePageController = loader.getController();
      moviePageController.setRemoteAccess(this.access);

      moviePageController.setMovieDetails(movieTitle);

      Stage stage = (Stage) moreInfobtn.getScene().getWindow();
      Scene scene = new Scene(parent);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the remote access object for interacting with the remote movie library service.
   *
   * @param access The {@link RemoteMovieLibraryAccess} object used for interacting with the remote library.
   */
  public void setRemoteAccess(final RemoteMovieLibraryAccess access) {
    this.access = access;
  }
}
