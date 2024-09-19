package movielibrary;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * MoviePageController handles the FXML
 */
public class MoviePageController {

    /**
     * FXML UI components on the MoviePage
     */
    @FXML
    private TextField MovieTitle;
    
    @FXML
    private TextArea Summary;
    
    @FXML
    private TextField MovieDuration;
    
    @FXML
    private Button Lendbtn;
    
    @FXML
    private Button Returnbtn;
    
    @FXML
    private Button Cancelbtn; 

    
    private MovieManager movieManager;
    private Movie movie;
    
   
    /**
     * Handles the movie title on the MoviePage.fxml.
     * @param movieTitle
     */
    public void setMovieDetails(String movieTitle, String description, double movieLength) {
        MovieTitle.setText(movieTitle);
        Summary.setText(description);
        MovieDuration.setText(String.valueOf(movieLength));
    }

    @FXML
    public void initialize() throws IOException{
        movieManager = new MovieManager();
    }
   

    /**
     * Handles the lend button, lends the movie. 
     */
    
    @FXML
        public void handleLendbtn(ActionEvent event) throws IOException {
            movie = movieManager.findMovie(MovieTitle.getText().strip());
            if (movieManager.checkIfRented(movie.getTitle())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Failed!");
                alert.setContentText("The movie is alredy rented!");
                alert.showAndWait();
            } else {
                movieManager.rent(movie.getTitle());
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Success!");
                alert.setContentText("Movie lend!");
                alert.showAndWait();
            }
            
            
    }

    /**
     * Handles the return button, returns the movie. 
     */
    
    @FXML
        public void handleReturnbtn(ActionEvent event) throws IOException {
            movie = movieManager.findMovie(MovieTitle.getText().strip());
            if (!movieManager.checkIfRented(movie.getTitle())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Failed!");
                alert.setContentText("You have not rented this movie.");
                alert.showAndWait();
            } else {
                movieManager.returnBack(movie.getTitle());
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Success!");
                alert.setContentText("Movie is returned!");
                alert.showAndWait();
            }
                
                
        }

    /**
     * Handles the cancel button, returning to the front page.
     * @param event
     * @throws IOException
     */
    
    @FXML
        public void returnToFrontPage(ActionEvent event) throws IOException {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Frontpage.fxml"));
                Parent root = loader.load();
    
                // get current stage and set the new scene (frontpage).
                Stage stage = (Stage) Cancelbtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        
    }
