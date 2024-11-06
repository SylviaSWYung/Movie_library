package movielibrary.ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import movielibrary.json.internal.MovieSerializer;

public class MoviePageTest extends ApplicationTest {

  private MoviePageController moviePageController;
  private Parent root;

  private MovieSerializer movieSerializer;
  private File temporaryFile;

  @BeforeAll
  public static void setUpHeadless() {
    App.supportHeadless();
  }

  // Deletes the temporaryFile after each test run
  @AfterEach
  public void deleteTemporaryFile() {
    temporaryFile.delete();
  }

  // Starts the movie page and loads the MoviePage.fxml file
  @Override
  public void start(Stage stage) throws Exception {
    // Creates a temporaryFile so the data doesn't get modified during test runs
    File sourceOfFile = new File("../core/src/main/resources/movielibrary/json/internal/moviesTest.json");
    temporaryFile = new File("../core/src/main/resources/movielibrary/json/internal/tempmovies.json");
    Files.copy(sourceOfFile.toPath(), temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

    movieSerializer = new MovieSerializer(temporaryFile);

    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("MoviePage.fxml"));
    root = fxmlLoader.load();
    moviePageController = fxmlLoader.getController();

    // Sets the temporaryFile as the file used during test runs
    moviePageController.setMovieFile(temporaryFile);

    // Sets default movie details for the movie page
    moviePageController.setMovieDetails("The Trollgirl", "When a girl is kidnapped by a troll, and turned into a troll. She meets the love of her life.", 100);

    stage.setScene(new Scene(root));
    stage.show();
  }

  // Set movie details test
  @Test
  public void testSetMovieDetails() {
    moviePageController.setMovieDetails("The Trollgirl", "When a girl is kidnapped by a troll, and turned into a troll. She meets the love of her life.", 100);
    WaitForAsyncUtils.waitForFxEvents();

    TextField movieTitleField = lookup("#movieTitleInPage").queryAs(TextField.class);
    assertThat(movieTitleField).hasText("The Trollgirl");

    TextArea summaryField = lookup("#summary").queryAs(TextArea.class);
    assertThat(summaryField).hasText("When a girl is kidnapped by a troll, and turned into a troll. She meets the love of her life.");

    TextField movieDurationField = lookup("#movieDuration").queryAs(TextField.class);
    assertThat(movieDurationField).hasText("100.0");
  }

  // Test lend movie
  // Should display an alert that the movie is lent and set the lending status to true
  @Test
  public void testLendMovie() throws IOException {
    movieSerializer.changeLentStatus("The Trollgirl", false);
    assertFalse(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be false");

    Button lendbtn = (Button) lookup("#lendbtn").query();  // Locate the button by its ID

    Platform.runLater(() -> {
      if (lendbtn != null) {
        lendbtn.fire();
      } else {
        System.out.println("Button is null");
      }
    });
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Movie lend!"));
    assertTrue(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be turned to true");
  }

  // Test return movie
  // Should display an alert that the movie is returned and set the lending status to false
  @Test
  public void testReturnMovie() throws IOException {
    movieSerializer.changeLentStatus("The Trollgirl", true);
    assertTrue(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be true");

    Button returnbtn = (Button) lookup("#returnbtn").query();  // Locate the button by its ID

    Platform.runLater(() -> {
      if (returnbtn != null) {
        returnbtn.fire();
      } else {
        System.out.println("Button is null");
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    
    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Movie is returned!"));
    assertFalse(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be turned to false");
  }

  // Test lend movie that is already lent
  // Should display an alert that the movie is already lent and keep the lending status as true
  @Test
  public void testLendAlreadyLentMovie() throws IOException {
    movieSerializer.changeLentStatus("The Trollgirl", true);
    assertTrue(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be true");
    
    Button lendbtn = (Button) lookup("#lendbtn").query();  // Locate the button by its ID

    Platform.runLater(() -> {
      if (lendbtn != null) {
        lendbtn.fire();
      } else {
        System.out.println("Button is null");
      }
    });
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("The movie is alredy lent!"));
    assertTrue(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should still be true");
  }

  // Test deleting movie success
  @Test
  public void testDeleteMovieSuccess() throws IOException {

    Button deletebtn = (Button) lookup("#deleteMoviebtn").query();  // Locate the button by its ID

    assertTrue(movieSerializer.movieIsFound("The Trollgirl"), "The movie is not in the library.");
    
    Platform.runLater(() -> {
      if (deletebtn != null) {
        deletebtn.fire();
      } else {
        System.out.println("Button is null");
      }
    });
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Movie is deleted!"));

    assertFalse(movieSerializer.movieIsFound("The Trollgirl"), "Movie is not deleted from the library.");
  }


  // Test return movie that is not lent
  // Should display an alert that the movie is not lent and keep the lending status as false
  @Test
  public void testReturnNotLentMovie() throws IOException {
    movieSerializer.changeLentStatus("The Trollgirl", false);
    assertFalse(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be false");

    Button returnbtn = (Button) lookup("#returnbtn").query();  // Locate the button by its ID

    Platform.runLater(() -> {
      if (returnbtn != null) {
        returnbtn.fire();
      } else {
        System.out.println("Button is null");
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    
    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("You have not lent this movie."));
    assertFalse(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should still be false");
  }

  // Test returning to the front page by pressing the cancel button
  // Verify that the elements from the movie page are present
  @Test
  public void testReturnToFrontPage() {
     Button cancelbtn = (Button) lookup("#cancelbtn").query();  // Locate the button by its ID

    Platform.runLater(() -> {
      if (cancelbtn != null) {
        cancelbtn.fire();
      } else {
        System.out.println("Button is null");
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    
    verifyThat("#movieScrollBar", NodeMatchers.isVisible());
    verifyThat("#moreInfobtn", NodeMatchers.isVisible());
  }

  // Test the IO exception that occurs when failing to return to the front page
  @Test
  public void testReturnToFrontPageFail() throws IOException {
    moviePageController.loadFrontPage(true);
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Could not load the page"));
  }
}
