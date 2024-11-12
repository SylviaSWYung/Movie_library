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
 * The {@code AddMoviePageController} handles the user interaction and logic for the
 * {@code AddMoviePage.fxml}. It provides functionality for adding a new movie to the 
 * movie library with the information entered by the user, and handles the behavior of
 * the UI components such as buttons and text fields. 
 *
 * <p>The page includes: 
 * <ul><li> A form to input movie details: length, and description. </li>
 * <li> A button to submit the movie information and add it to the library. </li> 
 * <li> A cancel button that returns the user to the {@code FrontPage.fxml} 
 * without making changes. </li></ul>
 */
public class AddMoviePageController {

  /**
   * Rest API object used for calling backend application.
   */
  private RemoteMovieLibraryAccess access = new RemoteMovieLibraryAccess();

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
   * Sets the remote access object for interacting with the remote movie library service.
   *
   * @param access The {@link RemoteMovieLibraryAccess} object used for interacting 
   *                with the remote library.
   */
  public void setRemoteAccess(final RemoteMovieLibraryAccess access) {
    this.access = access;
  }
  
  /**
   * Handles the "Add Movie" button click event. It validates the input fields for movie title,
   * length, and description. If any field is invalid, it shows an error alert. If the input is
   * valid, it creates a new {@link Movie} object and adds it to the library using 
   * the {@code access} object. If the movie title already exists or the input is invalid, 
   * it catches the respective exceptions and displays an appropriate error alert.
   *
   * @param event The event that will trigger this handler (button click).
   * @throws IOException If an I/O error occurs while accessing or adding the movie
   */
  public void addMovie(ActionEvent event) throws IOException {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Failed!");

    String error = null;
    if (newMovieTitle.getText().strip().isEmpty()) {
      error = "You must fill out the title of the new movie!";
    } else if (newMovieLength.getText().strip().isEmpty()) {
      error = "You must fill out the length of the new movie!";
    } else {
      try {
        int length = Integer.parseInt(newMovieLength.getText());
        if (length <= 0) {
          error = "Movie length must be greater than 0 minutes.";
        } else if (length > 120) {
          error = "The movie length can't exceed 120 minutes.";
        }
      } catch (NumberFormatException e) {
        error = "Invalid movie length format.";
      }
    }
    if (error == null) {
      String description = newMovieDescription.getText().strip();
      if (description.isEmpty()) {
        error = "You must fill out the description of the new movie!";
      } else if (description.length() < 20 || description.length() > 250) {
        error = "The description must be between 20 and 250 characters";
      }
    }

    if (error != null) {
      alert.setContentText(error);
      alert.showAndWait();
      return;
    }

    try {
      Movie movie = Movie.createMovie(newMovieTitle.getText(), 
          Integer.parseInt(newMovieLength.getText()), newMovieDescription.getText());
      access.addMovie(movie);
      showAlert(AlertType.INFORMATION, "Success!", "New movie is added to the library!");
      returnToFrontPage(event);
    } catch (IllegalStateException e) {
      error = e.getMessage();
      alert.setContentText("The movie title already exists in the movielibrary!");
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
   * Handles the {@code cancel} button click event. It navigates the user back to the frontpage.
   * This method calls the {@code returnToFrontPage} to load the front page {@code FrontPage.fxml}.
   *
   * @param event The event that trigger this handler (button click).
   * @throws IOException If an I/O error occurs while accessing the file
   */
  @FXML
  public void returnToFrontPage(ActionEvent event) throws IOException {
    loadFrontPage(false);
  }

  /**
   * Loads the front page {@code FrontPage.fxml} and sets the new scene i the current stage.
   * If {@code throwError} is true, it simulates an {@link IOException}.
   * Main purpose of the method is to load the front page and simulate an IOException.
   *
   * @param throwError A boolean that simulates an I/O error if true
   * @throws IOException If an error occurs while loading the FXML file or setting the new scene.
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

  /**
   * Shows an alert with the specified type, title, and message.
   * This is the utility method used to display informational or error alerts to the user.
   *
   * @param type The type of the alert (e.g., ERROR, INFORMATION).
   * @param title The title of the alert.
   * @param message The content message of the alert.
   */
  private void showAlert(Alert.AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }  
}
