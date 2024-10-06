package movielibrary.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import movielibrary.json.internal.MovieSerializer;

//Tests for MovieSerializer.java class
public class MovieSerializerTest {
    
    //MovieSerializer object for testing, File object for temporary file
    private MovieSerializer movieSerializer;
    private File temporaryFile;

    //Default setup for each test
    //Creates a temporary file (a copy of the original Movies.json) for the testing, and initializes the MovieSerializer object
    @BeforeEach
    public void setup() throws IOException {
        File sourceOfFile = new File("../core/src/main/resources/movielibrary/Movies.json");
        temporaryFile = new File("../core/src/main/resources/movielibrary/tempmovies.json");
        Files.copy(sourceOfFile.toPath(), temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        movieSerializer = new MovieSerializer(temporaryFile);
    }

    //Deletes the temporary file after each test
    @AfterEach
    public void deleteTemporaryFile() {
        temporaryFile.delete();
    }

    //Test serialize method of MovieSerializer.java
    @Test
    @DisplayName("Test-serialize")
    public void testSerializer() throws IOException {
        Assertions.assertFalse(movieSerializer.getLentStatus("Loverboy"));
        movieSerializer.serialize("Loverboy", true);
        Assertions.assertTrue(movieSerializer.getLentStatus("Loverboy"));
    }

}
