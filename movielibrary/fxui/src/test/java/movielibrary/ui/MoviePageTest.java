package movielibrary.ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;

import movielibrary.core.Movie;

public class MoviePageTest extends ApplicationTest {

  @Mock
  private RemoteMovieLibraryAccess mockedAccess = mock(RemoteMovieLibraryAccess.class);

  @InjectMocks
  private MoviePageController moviePageController;

  private Parent root;

  @BeforeAll
  public static void setUpHeadless() {
    App.supportHeadless();
  }

  // Starts the movie page and loads the MoviePage.fxml file
  @Override
  public void start(Stage stage) throws Exception {

    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("MoviePage.fxml"));
    root = fxmlLoader.load();
    moviePageController = fxmlLoader.getController();
    moviePageController.setRemoteAccess(mockedAccess);

    stage.setScene(new Scene(root));
    stage.show();
  }

  // Set movie details test
  @Test
  public void testSetMovieDetails() {
    Movie mockMovie = new Movie("Loverboy", 30, "Based on a true story, about a boy who marries his crush");
    when(mockedAccess.getMovieByTitle("Loverboy")).thenReturn(mockMovie);

    moviePageController.setMovieDetails("Loverboy");
    WaitForAsyncUtils.waitForFxEvents();

    TextField movieTitleField = lookup("#movieTitleInPage").queryAs(TextField.class);
    assertThat(movieTitleField).hasText("Loverboy");

    TextArea summaryField = lookup("#summary").queryAs(TextArea.class);
    assertThat(summaryField).hasText("Based on a true story, about a boy who marries his crush");

    TextField movieDurationField = lookup("#movieDuration").queryAs(TextField.class);
    assertThat(movieDurationField).hasText("30.0");
  }

  // Test to set movie details with a movie that is not found
  @Test
  public void testMovieNotFoundOnServer() {
    when(mockedAccess.getMovieByTitle("FakeMovie")).thenReturn(null);
    
    Platform.runLater(() -> {
      moviePageController.setMovieDetails("FakeMovie");
    });
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Movie not found on server."));
  }

  // Test lend movie
  // Should display an alert that the movie is lent and set the lending status to true
  @Test
  public void testLendMovie() throws IOException {
    when(mockedAccess.getMovieByTitle("Loverboy")).thenReturn(new Movie("Loverboy", 30.0, "Based on a true story, about a boy who marries his crush"));
    // Sets default movie details for the movie page
    moviePageController.setMovieDetails("Loverboy");

    when(mockedAccess.getLentStatus("Loverboy")).thenReturn(false);

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
    ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
    verify(mockedAccess).lendMovie(argumentCaptor.capture());
    assertThat(argumentCaptor.getValue()).isEqualTo("Loverboy");  // Verify the correct title was passed
  }

  // Test return movie
  // Should display an alert that the movie is returned and set the lending status to false
  @Test
  public void testReturnMovie() throws IOException {
    when(mockedAccess.getMovieByTitle("Loverboy")).thenReturn(new Movie("Loverboy", 30.0, "Based on a true story, about a boy who marries his crush"));
    // Sets default movie details for the movie page
    moviePageController.setMovieDetails("Loverboy");
    when(mockedAccess.getLentStatus("Loverboy")).thenReturn(true);

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
    verify(mockedAccess).returnMovie("Loverboy");
  }

  // Test lend movie that is already lent
  // Should display an alert that the movie is already lent and keep the lending status as true
  @Test
  public void testLendAlreadyLentMovie() throws IOException {
    when(mockedAccess.getMovieByTitle("Loverboy")).thenReturn(new Movie("Loverboy", 30.0, "Based on a true story, about a boy who marries his crush"));
    // Sets default movie details for the movie page
    moviePageController.setMovieDetails("Loverboy");
    when(mockedAccess.getLentStatus("Loverboy")).thenReturn(true);
    
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
    verifyThat(".alert .content", hasText("The movie is already lent!"));
    verify(mockedAccess, never()).lendMovie("Loverboy");
  }

  // Test deleting movie success
  @Test
  public void testDeleteMovieSuccess() throws IOException {
    when(mockedAccess.getMovieByTitle("Loverboy")).thenReturn(new Movie("Loverboy", 30.0, "Based on a true story, about a boy who marries his crush"));
    // Sets default movie details for the movie page
    moviePageController.setMovieDetails("Loverboy");

    Button deletebtn = (Button) lookup("#deleteMoviebtn").query();  // Locate the button by its ID
    
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
    verify(mockedAccess).deleteMovie("Loverboy");
  }


  // Test return movie that is not lent
  // Should display an alert that the movie is not lent and keep the lending status as false
  @Test
  public void testReturnNotLentMovie() throws IOException {
    when(mockedAccess.getMovieByTitle("Loverboy")).thenReturn(new Movie("Loverboy", 30.0, "Based on a true story, about a boy who marries his crush"));
    // Sets default movie details for the movie page
    moviePageController.setMovieDetails("Loverboy");
    when(mockedAccess.getLentStatus("Loverboy")).thenReturn(false);

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
    verifyThat(".alert .content", hasText("The movie is not lent."));
    verify(mockedAccess, never()).returnMovie("Loverboy");
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
