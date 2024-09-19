package movielibrary;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;

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
    
    private Movie selectedMovie;
    private Movie movie;
    private String title;
    private double movieLength;
    private String description;
    

    /**
     * Initialize the MoviePage. Creates a MovieManager object to access the data from Movies.csv.
     * Adds all movietitles from the data in Movies.csv to the List movieTitles.
     * @throws 
     */

    @FXML
     public void initialize() throws IOException {
         try {
             // accesses the movietitles from the Movies.csv file through the MovieManager
             MovieManager movieman = new MovieManager();
         
             List<String> movieTitles = new ArrayList<>();
             for (Movie mov : movieman.getMovies()) {
                 movieTitles.add(mov.getTitle());
             }
 
         } catch (Exception e){
             e.printStackTrace();
         }
    }
    
    /**
     * Sets the movie detalis (title, description, and duration) when a movie is selected. 
     * @param selectMovie 
     */

    @FXML
    public void setMovieDetails(Movie selectMovie) {
        this.selectedMovie = selectMovie;
    
        MovieTitle.setText(selectMovie.getTitle());
        Summary.setText(selectedMovie.getDescription());
        MovieDuration.setText(String.valueOf(selectedMovie.getMovieLength()));
    }
    
    @FXML
    public void setTitle(String title) {
        // adds the movieTitle to the MovieTitlebox.
        this.title = title;
        MovieTitle.setText(this.title);
    }
    
    
    @FXML
    public void setDescription(String description) {
        // adds the movie description to the MovieTextArea.
        this.description = description;
        Summary.setText(this.description);
    }
        
    @FXML
    public void setMovieLength(double length) {
        // adds the movie length to the MovieTextField. 
        this.movieLength = length;
        // MovieDuration.setViewOrder((this.movieLength));
        MovieDuration.setText(String.valueOf(this.movieLength));
    }

    /**
     * Handles the lend button, lends the movie. 
     */
    
    @FXML
        public void setRented() {
    
    }

    /**
     * Handles the return button, returns the movie. 
     */
    
    @FXML
        public void returnBack() {
    
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
