package movielibrary.ui;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import movielibrary.core.Movie;
import movielibrary.json.internal.MovieDeserializer;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.util.List;
import java.util.ArrayList;

/**
 * FrontPageController handles the FXML file
 */

public class FrontPageController {
    
    /**
     * FXML UI components on the FrontPage
     */

    @FXML
    private Button MoreInfobtn;

    @FXML
    private ChoiceBox<String> MovieScrollBar;

    /**
     * Decleare movieDeserializer variable of type MovieDeserializer and movie variable of type Movie
     */

    private MovieDeserializer movieDeserializer;
    private Movie movie;

    /**
     * Initialize the FrontPage. Creates a MovieDeserializer object to access the data from Movies.json.
     * Adds all movietitles from the data in Movies.json to the List movieTitles.
     * Adds the MovieTitle elements as items in the MovieScrollBar
     */

    @FXML
    public void initialize() throws IOException {
        try {
            File jsonFile = new File("../core/src/main/resources/movielibrary/Movies.json");
            movieDeserializer = new MovieDeserializer(jsonFile);

            List<String> movieTitles = new ArrayList<>();
            for (Movie mov : movieDeserializer.getMoviesInLibrary()) {
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
     * Throws error if the user has not chosen a movie before clicking MoreInforbtn
     * If the user has chosen a movie and clicks the MoreInfobtn, the FrontPage.fxml will load to MoviePage.fxml
     */

    @FXML
    public void handleMoreInfoButton() throws IOException {
            String chosenMovie = MovieScrollBar.getValue();
            movie = movieDeserializer.findMovie(chosenMovie);
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
     * loadPage method for redirecting and loading the MoviePage.fxml
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
