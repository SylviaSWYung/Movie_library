package movielibrary.ui;

import java.io.File;
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
import movielibrary.json.internal.MovieDeserializer;

/**
 * The {@code FrontPageController} handles the FXML file {@code FrontPage} and it's behaviour.
 * Handles the user interaction with the UI components on the FrontPage.
 * The user interaction includes choosing a movie from the {@code ChoiceBox} 
 * and clicking the {@code MoreInfo} button.
 */
public class FrontPageController {
    
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
   * Declare movieDeserializer variable of type {@link MovieDeserializer} 
   * and movie variable of type {@link Movie}.
   */
  private MovieDeserializer movieDeserializer;
  private Movie movie;

  /**
   * Initialize the FrontPage. Creates a {@link MovieDeserializer} object 
   * to access the data from {@code Movies.json} file stored in the 
   * user's home directory
   * Retrieves all movie titles from the data in {@code Movies.json}
   * and adds them to the list {@code movieTitles}.
   * The movie titles are then added as items in the {@code MovieScrollBar}
   *
   * @throws IOException if an I/O error occurs while accessing the {@code movies.json} file
   */
  @FXML
  public void initialize() throws IOException {
    try {
      File jsonFile = new File(
          System.getProperty("user.home") 
          + System.getProperty("file.separator")
          + "movies.json"
      );
      movieDeserializer = new MovieDeserializer(jsonFile);

      List<String> movieTitles = new ArrayList<>();
      for (Movie mov : movieDeserializer.getMoviesInLibrary()) {
        movieTitles.add(mov.getTitle());
      }
      movieScrollBar.getItems().addAll(movieTitles);

    } catch (Exception e) {
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
      movie = movieDeserializer.findMovie(chosenMovie);
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

      AddMoviePageController addMoviePageController = loader.getController();
      addMoviePageController.setMovieFile(null);

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

      //Sets the file to null so it initializes movies.json file in user.home
      moviePageController.setMovieFile(null);

      moviePageController.setMovieDetails(movieTitle, description, movieLength);

      Stage stage = (Stage) moreInfobtn.getScene().getWindow();
      Scene scene = new Scene(parent);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
