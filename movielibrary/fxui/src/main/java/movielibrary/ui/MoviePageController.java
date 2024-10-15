package movielibrary.ui;

import java.io.File;
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
import movielibrary.json.internal.MovieDeserializer;
import movielibrary.json.internal.MovieManager;
import movielibrary.json.internal.MovieSerializer;


/**
 * The {@code MoviePageController} handles the FXML file {@code MoviePage} and it's behaviour.
 * Handles the user interaction with the UI components on the MoviePage.
 * The user interaction includes lending a movie with the {@code lend} button, 
 * and returning a lend movie with {@code return} button. 
 * If the user wants to cancel the chosen movie, user can click on the {@code cancel} button.  
 */
public class MoviePageController {

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
   * Declare movieDeserializer variable of type {@link MovieDeserializer}, 
   * and movieSerializer variable of type {@link MovieSerializer}.
   * Declare movieManager variable of type {@link MovieManager}, 
   * and movie variable of type {@link Movie}.
   */
  private MovieDeserializer movieDeserializer;
  private MovieSerializer movieSerializer;
  private MovieManager movieManager;
  private Movie movie;
  
  
  /**
   * Handles the movie data {@code movieTitle}, {@code description} 
   * and {@code movieLength} from the chosen movie.
   * Sets the movie data to the corresponing FXML UI component on the {@code MoviePage.fxml}.
   *
   * @param movieTitle A String with the chosen movie title
   * @param description A String with the description of the chosen movie
   * @param movieLength A double with the length of the chosen movie
   */
  public void setMovieDetails(String movieTitle, String description, double movieLength) {
    movieTitleInPage.setText(movieTitle);
    summary.setText(description);
    movieDuration.setText(String.valueOf(movieLength));
  }

  /**
   * Initialize the {@code MoviePage.fxml}.
   * Creates objects of movieDeserializer, movieSerializer and movieManager for datahandling.
   *
   * @throws IOException Throws IOException if an I/O error occurs while accessing the file
   */
  @FXML
  public void initialize() throws IOException {
    File jsonFile = new File("../core/src/main/resources/movielibrary/movies.json");
    movieDeserializer = new MovieDeserializer(jsonFile);
    movieSerializer = new MovieSerializer(jsonFile);
    movieManager = new MovieManager();
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
    movie = movieDeserializer.findMovie(movieTitleInPage.getText().strip());
    if (movieDeserializer.checkIfLent(movie.getTitle())) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Failed!");
      alert.setContentText("The movie is alredy lent!");
      alert.showAndWait();
    } else {
      movieManager.lend(movie.getTitle());
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Success!");
      alert.setContentText("Movie lend!");
      alert.showAndWait();
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
    movie = movieDeserializer.findMovie(movieTitleInPage.getText().strip());
    if (!movieDeserializer.checkIfLent(movie.getTitle())) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Failed!");
      alert.setContentText("You have not lent this movie.");
      alert.showAndWait();
    } else {
      movieManager.returnBack(movie.getTitle());
      movieSerializer.changeLentStatus(movie.getTitle(), false);
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Success!");
      alert.setContentText("Movie is returned!");
      alert.showAndWait();
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
