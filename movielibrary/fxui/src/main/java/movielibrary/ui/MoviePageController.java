package movielibrary.ui;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import movielibrary.core.Movie;

/**
 * The {@code MoviePageController} handles the FXML file {@code MoviePage} and it's behaviour.
 * Handles the user interaction with the UI components on the MoviePage.
 * The user interaction includes lending a movie with the {@code lend} button, 
 * and returning a lend movie with {@code return} button. 
 * If the user wants to cancel the chosen movie, user can click on the {@code cancel} button.  
 */
public class MoviePageController {

  private RemoteMovieLibraryAccess access = new RemoteMovieLibraryAccess();

  /**
   * FXML UI components on the MoviePage.
   * {@code MovieTitle} is a textfield that gives the chosen movie title.
   */
  @FXML
  private TextField movieTitleInPage;
  
  /**
   * FXML UI components on the MoviePage.
   * {@code Summary} is a textarena that gives the chosen movie summary and description.
   */
  @FXML
  private TextArea summary;
  
  /**
   * FXML UI components on the MoviePage.
   * {@code MovieDuration} is a textfield that gives the chosen movie duration.
   */
  @FXML
  private TextField movieDuration;
  
  /**
   * FXML UI components on the MoviePage.
   * {@code Lendbtn} is a button used for lending the chosen movie.
   */
  @FXML
  private Button lendbtn;
  
  /**
   * FXML UI components on the MoviePage.
   * {@code Returnbtn} is a button used for returning the movie.  
   */
  @FXML
  private Button returnbtn;
  
  /**
   * FXML UI components on the MoviePage.
   * {@code Cancelbtn} is a button that returns the user back to {@code FrontPage.fxml}.
   */
  @FXML
  private Button cancelbtn;

  /**
   * FXML UI components on the MoviePage.
   * {@code deleteMoviebtn} is a button that delete the chosen movie 
   * and returns the user back to {@code FrontPage.fxml}.
   */
  @FXML
  private Button deleteMoviebtn;

  /**
   * Sets the details of the movie that is to be displayed.
   * Fetches the rest of the information from the {@link RemoteMovieLibraryAccess} object
   * used for interacting with the remote library.
   *
   * @param movieTitle the title of the movie to be displayed
   */
  public void setMovieDetails(String movieTitle) {
    Movie movie = access.getMovieByTitle(movieTitle);
    if (movie != null) {
      movieTitleInPage.setText(movie.getTitle());
      summary.setText(movie.getDescription());
      movieDuration.setText(String.valueOf(movie.getMovieLength()));
    } else {
      showAlert(AlertType.ERROR, "Error", "Movie not found on server.");
    }
  }

  /**
   * Sets the remote access object for interacting with the remote movie library service.
   *
   * @param access The {@link RemoteMovieLibraryAccess} 
    object used for interacting with the remote library.
   */
  public void setRemoteAccess(final RemoteMovieLibraryAccess access) {
    this.access = access;
  }

  /**
   * Handles the {@code lent} button, and lends the movie. 
   * Finds the chosen movie and checks if it is lent.
   * If the movie is already lend, the program will give an error alert.
   * If the movie is not lend, it sets the movie as lent and gives a confirmation alert.
   *
   * @throws IOException Throws IOException if an I/O error occurs while accessing the file
   */
  @FXML
  public void handleLendbtn(ActionEvent event) throws IOException {
    String title = movieTitleInPage.getText();
    if (access.getLentStatus(title)) {
      showAlert(AlertType.ERROR, "Failed", "The movie is already lent!");
    } else {
      access.lendMovie(title);
      showAlert(AlertType.INFORMATION, "Success", "Movie lend!");
    }
  }

  /**
   * Handles the {@code return} button, returns the movie. 
   * Finds the chosen movie and checks if it is lent.
   * If the movie is not lent, the program will give an error alert. If the movie is lent, 
   * it sets the movie as not lent (returns) and gives a confirmation alert.
   *
   * @throws IOException Throws IOException if an I/O error occurs while accessing the file
   */
  @FXML
  public void handleReturnbtn(ActionEvent event) throws IOException {
    if (!access.getLentStatus(movieTitleInPage.getText())) {
      showAlert(AlertType.ERROR, "Failed!", "The movie is not lent.");
    } else {
      access.returnMovie(movieTitleInPage.getText());
      showAlert(AlertType.INFORMATION, "Success!", "Movie is returned!");
    }    
  }

  /**
   * Handles the {@code Delete} button and deletes the movie on the page. 
   * Runs the {@code returnToFrontPage} method to load the front page {@code FrontPage.fxml}.
   *
   * @throws IOException Throws IOException if an I/O error occurs while accessing the file
   */
  @FXML
  public void handleDeleteMoviebtn(ActionEvent event) throws IOException {
    try {
      String movieTitle = movieTitleInPage.getText();
      access.deleteMovie(movieTitle);
      showAlert(AlertType.INFORMATION, "Success!", "Movie is deleted!");
      returnToFrontPage(event);
    } catch (IllegalStateException e) {
      showAlert(AlertType.ERROR, "Failed!", e.getMessage());
    }
  } 

  /**
   * Handles the {@code cancel} button, returning to the front page.
   * Runs the {@code returnToFrontPage} method to load the front page {@code FrontPage.fxml}.
   *
   * @param event The event that will trigger this handler, in this case a button click user action.
   * @throws IOException Throws IOException if an I/O error occurs while accessing the file
   */
  @FXML
  public void returnToFrontPage(ActionEvent event) throws IOException {
    loadFrontPage(false);
  }
  
  /**
   * Loads the front page {@code FrontPage.fxml} and sets the new stage to this page.
   * Main purpose of the method is to load the front page and simulate an IOException.
   *
   * @param throwError A boolean that simulates an IOException if true
   * @throws IOException Throws IOException if an I/O error occurs while accessing the file
   */
  public void loadFrontPage(boolean throwError) throws IOException {
    try {
      if (throwError) {
        throw new IOException("Simulated IOException");
      }
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/movielibrary/ui/FrontPage.fxml"));
      Parent root = loader.load();

      FrontPageController frontPageController = loader.getController();
      frontPageController.initializes();

      // get current stage and set the new scene (frontpage).
      Stage stage = (Stage) cancelbtn.getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      Platform.runLater(() -> {
        showAlert(Alert.AlertType.ERROR, "Error", "Could not load the page");
      });
    }
  }

  private void showAlert(Alert.AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }  
}
