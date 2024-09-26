package movielibrary.json;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import movielibrary.core.Movie;
import movielibrary.json.internal.MovieManagerCSV;

import java.util.ArrayList;
import java.util.List;

public class MovieManagerTest {
    
    //MovieManager object for testing
    private MovieManagerCSV movieManager;

    //Default setup for each test
    @BeforeEach
    public void setup() throws IOException {
        try {
            movieManager = new MovieManagerCSV();
        } catch (IOException e) {
            fail("Exception occurred during setup: " + e.getMessage());
        }
        
    }

    //Tests if getMovies() in MovieManager.java retrieves the right list with right Movie objects
    @Test
    @DisplayName("Get Movies")
    public void testGetMovies() {
        Movie m1 = movieManager.findMovie("The Trollgirl");
        Movie m2 = movieManager.findMovie("Loverboy");

        List<Movie> listOfMovies = new ArrayList<Movie>(List.of(m1, m2));

        Assertions.assertEquals(listOfMovies, movieManager.getMovies());
    }

    //Testing the default value false in Movie.csv
    @Test
    @DisplayName("Read from File")
    public void testReadFromFile() throws IOException {
        Assertions.assertFalse(movieManager.checkIfRented("The Trollgirl"));
        Assertions.assertFalse(movieManager.checkIfRented("Loverboy"));
    }

    //Testing rent and returnBack method for each movie
    //Testing if the value in Movie.csv changes to true and back to false
    @Test
    @DisplayName("Write to File")
    public void testWriteToFile() throws IOException {
        movieManager.rent("The Trollgirl");
        Assertions.assertTrue(movieManager.checkIfRented("The Trollgirl"));

        movieManager.rent("Loverboy");
        Assertions.assertTrue(movieManager.checkIfRented("Loverboy"));

        movieManager.returnBack("The Trollgirl");
        Assertions.assertFalse(movieManager.checkIfRented("The Trollgirl"));

        movieManager.returnBack("Loverboy");
        Assertions.assertFalse(movieManager.checkIfRented("Loverboy"));
    }

}
