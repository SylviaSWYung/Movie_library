package movielibrary;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieManagerTest {
    
    private MovieManager movieManager;

    @BeforeEach
    public void setup() throws IOException {
        try {
            movieManager = new MovieManager();
        } catch (IOException e) {
            fail("Exception occurred during setup: " + e.getMessage());
        }
        
    }

    @Test
    @DisplayName("Read from File")
    public void testReadFromFile() throws IOException {
        Assertions.assertFalse(movieManager.checkIfRented("The Trollgirl"));
        Assertions.assertFalse(movieManager.checkIfRented("Loverboy"));
    }

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
