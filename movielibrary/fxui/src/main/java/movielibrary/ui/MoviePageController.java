package movielibrary.ui;

import java.io.File;
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
import movielibrary.core.Movie;
import movielibrary.json.internal.MovieDeserializer;
import movielibrary.json.internal.MovieManager;
import movielibrary.json.internal.MovieSerializer;

/**
 * MoviePageController handles the FXML file (MoviePage.fxml)
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

    /**
     * Declare movieDeserializer variable of type MovieDeserializer
     * Declare movieSerializer variable of type MovieSerializer
     * Declare movieManager variable of type MovieManager
     * Declare movie variable of type Movie
     */
    private MovieDeserializer movieDeserializer;
    private MovieSerializer movieSerializer;
    private MovieManager movieManager;
    private Movie movie;
    
   
    /**
     * Handles the movie data (movieTitle, description and movieLength) from the chosen movie.
     * Sets the movie data to the corresponing FXML UI component on the MoviePage.fxml
     * @param movieTitle A String with the chosen movie title
     * @param description A String with the description of the chosen movie
     * @param movielength A String with the lenght of the chosen movie
     */
    public void setMovieDetails(String movieTitle, String description, double movieLength) {
        MovieTitle.setText(movieTitle);
        Summary.setText(description);
        MovieDuration.setText(String.valueOf(movieLength));
    }

    /**
     * Initialize the MoviePage.fxml
     * Creates objects: movieDeserializer, movieSerializer and movieManager for datahandling
     * @throws IOException Throws IOException if an I/O error occurs while accessing the file
     */
    @FXML
    public void initialize() throws IOException{
        File jsonFile = new File("../core/src/main/resources/movielibrary/Movies.json");
        movieDeserializer = new MovieDeserializer(jsonFile);
        movieSerializer = new MovieSerializer(jsonFile);
        movieManager = new MovieManager();
    }
   

    /**
     * Handles the lend button, lends the movie.
     * Finds the chosen movie and checks if it is rented
     * If the movie is already lent, the program will give an error alert
     * If the movie is not lent, it sets the movie as lent and gives a confirmation alert
     * @throws IOException Throws IOException if an I/O error occurs while accessing the file
     */
    
    @FXML
        public void handleLendbtn(ActionEvent event) throws IOException {
            movie = movieDeserializer.findMovie(MovieTitle.getText().strip());
            if (movieDeserializer.checkIfRented(movie.getTitle())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Failed!");
                alert.setContentText("The movie is alredy rented!");
                alert.showAndWait();
            } else {
                movieManager.rent(movie.getTitle());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Movie lend!");
                alert.showAndWait();
            }
            
            
    }

    /**
     * Handles the return button, returns the movie. 
     * Finds the chosen movie and checks if it is rented
     * If the movie is not lent, the program will give an error alert
     * If the movie is lent, it sets the movie as not lent (returns) and gives a confirmation alert
     * @throws IOException Throws IOException if an I/O error occurs while accessing the file
     */
    
    @FXML
        public void handleReturnbtn(ActionEvent event) throws IOException {
            movie = movieDeserializer.findMovie(MovieTitle.getText().strip());
            if (!movieDeserializer.checkIfRented(movie.getTitle())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Failed!");
                alert.setContentText("You have not rented this movie.");
                alert.showAndWait();
            } else {
                movieManager.returnBack(movie.getTitle());
                movieSerializer.serialize(movie.getTitle(), false);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Success!");
                alert.setContentText("Movie is returned!");
                alert.showAndWait();
            }
                
                
        }

    /**
     * Handles the cancel button, returning to the front page.
     * Accesses the FrontPage.fxml file and sets the new stage to this page
     * @param event The event that will trigger this handler, in this case a button click user action
     * @throws IOException Throws IOException if an I/O error occurs while accessing the file
     */
    
    @FXML
        public void returnToFrontPage(ActionEvent event) throws IOException {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/movielibrary/ui/Frontpage.fxml"));
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
