package movielibrary.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.File;
import java.io.IOException;

import movielibrary.json.internal.MovieSerializer;

public class MoviePageTest extends ApplicationTest {

  private MoviePageController moviePageController;
  private Parent root;

  private MovieSerializer movieSerializer;

  // accessing the Movies.json file to be able to check the lent status of the movie
  // create an instance of the MovieSerializer class modify and check the lent status of the movie
  @BeforeEach
  public void setUp() throws IOException {
    File movieFile = new File("../core/src/main/resources/movielibrary/Movies.json");
    movieSerializer = new MovieSerializer(movieFile);
  }

  // starts the movie page and loads the MoviePage.fxml file
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("MoviePage.fxml"));
    root = fxmlLoader.load();
    moviePageController = fxmlLoader.getController();
    // sets default movie details for the movie page
    moviePageController.setMovieDetails("The Trollgirl", "When a girl is kidnapped by a troll, and turned into a troll. She meets the love of her life.", 100);

    stage.setScene(new Scene(root));
    stage.show();
  }

  // set movie details test
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

  // test lend movie
  // should display an alert that the movie is lent and set the lending status to true
  @Test
  public void testLendMovie() throws IOException {
    movieSerializer.serialize("The Trollgirl", false);
    assertFalse(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be false");
    clickOn(LabeledMatchers.hasText("Lend"));
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Movie lend!"));
    assertTrue(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be turned to true");
  }

  // test return movie
  // should display an alert that the movie is returned and set the lending status to false
  @Test
  public void testReturnMovie() throws IOException {
    movieSerializer.serialize("The Trollgirl", true);
    assertTrue(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be true");
    clickOn(LabeledMatchers.hasText("Return"));
    WaitForAsyncUtils.waitForFxEvents();
    
    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Movie is returned!"));
    assertFalse(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be turned to false");
  }

  // test lend movie that is already lent
  // should display an alert that the movie is already lent and keep the lending status as true
  @Test
  public void testLendAlreadyLentMovie() throws IOException {
    movieSerializer.serialize("The Trollgirl", true);
    assertTrue(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be true");
    clickOn(LabeledMatchers.hasText("Lend"));
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("The movie is alredy lent!"));
    assertTrue(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should still be true");
  }

  // test return movie that is not lent
  // should display an alert that the movie is not lent and keep the lending status as false
  @Test
  public void testReturnNotLentMovie() throws IOException {
    movieSerializer.serialize("The Trollgirl", false);
    assertFalse(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should be false");
    clickOn(LabeledMatchers.hasText("Return"));
    WaitForAsyncUtils.waitForFxEvents();
    
    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("You have not lent this movie."));
    assertFalse(movieSerializer.getLentStatus("The Trollgirl"), "Movie lending status should still be false");
  }

  // test returning to the front page by pressing the cancel button
  // verify that the elements from the movie page are present
  @Test
  public void testReturnToFrontPage() {
    clickOn(LabeledMatchers.hasText("Cancel"));
    WaitForAsyncUtils.waitForFxEvents();
    
    verifyThat("#movieScrollBar", NodeMatchers.isVisible());
    verifyThat("#moreInfobtn", NodeMatchers.isVisible());
  }

  // test the IO exception that occurs when failing to return to the front page
  @Test
  public void testReturnToFrontPageFail() throws IOException {
    moviePageController.loadFrontPage(true);
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Could not load the page"));
  }
}
