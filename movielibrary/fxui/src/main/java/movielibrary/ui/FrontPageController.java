package util;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.util.List;
import java.util.ArrayList;

/**
 * FrontPageController handles the FXML
 */

public class FrontPageController {
    
    /**
     * FXML UI components on the FrontPage
     */

    @FXML
    private Button MoreInfobtn;

    @FXML
    private ChoiceBox<String> MovieScrollBar;

    private MovieManager movieManager;
    private Movie movie;

    /**
     * Initialize the FrontPage. Creates a MovieManager object to access the data from Movies.csv.
     * Adds all movietitles from the data in Movies.csv to the List movieTitles.
     * Adds the MovieTitle elements as items in the MovieScrollBar
     */

    @FXML
    public void initialize() throws IOException {
        try {
            // accesses the movietitles from the Movies.csv file through the MovieManager
            movieManager = new MovieManager();
        
            List<String> movieTitles = new ArrayList<>();
            for (Movie mov : movieManager.getMovies()) {
                movieTitles.add(mov.getTitle());
            }

            // adds the movieTitles in the ChoiceBox
            MovieScrollBar.getItems().addAll(movieTitles);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Handles MoreInfobtn OnAction
     */

    @FXML
    public void handleMoreInfoButton() throws IOException {
            String chosenMovie = MovieScrollBar.getValue();
            movie = movieManager.findMovie(chosenMovie);
            if (chosenMovie != null && !chosenMovie.isEmpty()) {
                loadPage("MoviePage.fxml", movie.getTitle(), movie.getDescription(), movie.getMovieLength());
            } else {
                Alert alert = new Alert(AlertType.ERROR, "Please chose a movie from the scrollbar menu");
                alert.setTitle("Error");
                alert.setHeaderText("No movie chosen");
                alert.showAndWait();
            }
    }

    /**
     * loadPage method for redirecting and loading another .fxml page
     * 
     * @param page A String with the .fxml file
     */

    public void loadPage(String page, String movieTitle, String description, double movieLength) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + page));
            Parent parent = loader.load();

            MoviePageController moviePageController = loader.getController();

            moviePageController.setMovieDetails(movieTitle, description, movieLength);

            Stage stage = (Stage) MoreInfobtn.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
