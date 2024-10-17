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
import movielibrary.json.internal.MovieManager;

/**
 * The {@code AddMoviePageController} handles the FXML file {@code AddMoviePage} and its behaviour.
 * Handles the user interaction with the UI components on the AddMoviePage.
 * The page include adding a movie to the movielibrary with inforamtion from the user
 * and with a {@code addMoviebtn} button.
 * If the user wants to cancel to add a movie, user can click on the {@code cancel} button.
 */
public class AddMoviePageController {

  /**
   * FXML UI component on the MoviePage.
   * {@code newMovieTitle} is a textfield that sets the new movie title.
   */
  @FXML
  private TextField newMovieTitle;

  /**
   * FXML UI component on the MoviePage.
   * {@code newMovieLength} is a textfield that sets the new movie length.
   */
  @FXML
  private TextField newMovieLength;

  /**
   * FXML UI component on the MoviePage.
   * {@code newMovieDescription} is a textarea that sets the new description of the movie.
   */
  @FXML
  private TextArea newMovieDescription;

  /**
   * FXML UI component on the MoviePage.
   * {@code addMoviebtn} a button that adds the new movie to the movie library.
   */
  @FXML
  private Button addMoviebtn;
  
  /**
   * FXML UI component on the MoviePage.
   * {@code Cancelbtn} is a button that returns the user back to {@code FrontPage.fxml}.
   */
  @FXML
  private Button cancelbtn;

  /**
   * Declare movieManager variable of type {@link MovieManager}.
   */
  private MovieManager movieManager;

  /**
   * Initializes the AppMoviePage. Creates a {@link movieManager} object.
   *
   * @throws IOException Throws IOException if an I/O error occurs while accessing the file
   */
  public void initialize() throws IOException {
    movieManager = new MovieManager();
  }

  /**
   * Gives an error alert if the movie title, movie length and movie description is empty.
   * Gives an information alert when the movie is succsessfully added. 
   * Catches an IllegalStateException if the movie title already exists.
   * Catches an IllegalArgumentException if the movie length is set as invalid 
   * and the description isnt between 20-250 characters.
   *
   * @param event The event that will trigger this handler, in this case a button click user action.
   * @throws IOException Throws IOException if an I/O error occurs while accessing the file
   */
  public void addMovie(ActionEvent event) throws IOException {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Failed!");

    String error = null;
    if (newMovieTitle.getText().strip().isEmpty()) {
      error = "You must fill out the title of the new move!";
    } else if (newMovieLength.getText().strip().isEmpty()) {
      error = "You must fill out the length of the new movie!";
    } else if (newMovieDescription.getText().strip().isEmpty()) {
      error = "You must fill out the desciption of the new movie!";
    } 
    
    try {
      if (error != null) {
        alert.setContentText(error);
        alert.showAndWait();
        return;
      } else {
        movieManager.addMovie(newMovieTitle.getText(), 
            Integer.parseInt(newMovieLength.getText()), newMovieDescription.getText());
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setContentText("New movie is added to the library!");
        alert.showAndWait();
        returnToFrontPage(event);
      }
    } catch (IllegalStateException e) {
      error = "The movie title already exists!";
      alert.setContentText(error);
      alert.showAndWait();
      return;
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
      alert.setContentText(error);
      alert.showAndWait();
      return;
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
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/movielibrary/ui/Frontpage.fxml"));
      Parent root = loader.load();

      // get current stage and set the new scene (frontpage).
      Stage stage = (Stage) cancelbtn.getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Could not load the page");
        alert.showAndWait();
      });
    }
  }

}
