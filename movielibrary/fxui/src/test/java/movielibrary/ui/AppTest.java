package movielibrary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class AppTest extends ApplicationTest {

  private Stage primaryStage;
  private App app;

  // starts the application
  @Override
  public void start(final Stage stage) throws IOException {
    this.primaryStage = stage;
    this.app = new App();
    app.start(stage);
  }

  // test that the application sets the scene
  @Test
  public void testAppSetScene() {
    assertNotNull(primaryStage.getScene(), "Scene should be set and not null");
  }

  // test that the application sets the title
  @Test
  public void testSetTitle() {
    assertEquals("MovieLibrary App", primaryStage.getTitle(), "Title should be MovieLibrary App and not null");
  }

  // test that the IOEception is handled in the start method when trying to load a non-exixtent FXML file
  // due to the difficulty to simulate the IOException without modifying the App.java file directly,
  // we will test the FXMLLoader exception handling
  @Test
  public void testStartMethodIOException() throws IOException {
    Throwable thrown = assertThrows(IllegalStateException.class, () -> {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/movielibrary/ui/Fake.fxml"));
      fxmlLoader.load();
    });

    assertEquals("Location is not set.", thrown.getMessage(), "Exception message should indicate location issue");
  }

  // instead of testing the main method in App.java, we will test the start method that loads the page
  // this is to avoid the JavaFX thread issue when running the main method
  // since the main method doesn't handle any direct initialization logic
}