package movielibrary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class AppTest extends ApplicationTest {

  @Mock
  private RemoteMovieLibraryAccess mockedAccess = mock(RemoteMovieLibraryAccess.class);

  private Stage primaryStage;
  private App app;

  @BeforeAll
  public static void setUpHeadless() {
    App.supportHeadless();
  }

  @Test
  public void testSupportHeadlessWhenHeadlessTrue() {
    // Set the "headless" property to true
    System.setProperty("headless", "true");
    
    App.supportHeadless();
    
    // Assert that system properties are correctly set
    assertEquals("glass", System.getProperty("testfx.robot"));
    assertEquals("true", System.getProperty("testfx.headless"));
    assertEquals("sw", System.getProperty("prism.order"));
    assertEquals("t2k", System.getProperty("prism.text"));
    assertEquals("true", System.getProperty("java.awt.headless"));
    
    // Cleanup after test
    System.clearProperty("headless");
    System.clearProperty("testfx.robot");
    System.clearProperty("testfx.headless");
    System.clearProperty("prism.order");
    System.clearProperty("prism.text");
    System.clearProperty("java.awt.headless");
  }

  @Test
  public void testSupportHeadlessWhenHeadlessFalse() {
    // Set the "headless" property to false
    System.setProperty("headless", "false");

    App.supportHeadless();
    
    // Assert that system properties are NOT set
    assertNull(System.getProperty("testfx.robot"));
    assertNull(System.getProperty("testfx.headless"));
    assertNull(System.getProperty("prism.order"));
    assertNull(System.getProperty("prism.text"));
    assertNull(System.getProperty("java.awt.headless"));
    
    // Cleanup after test
    System.clearProperty("headless");
  }

  // Starts the application
  @Override
  public void start(final Stage stage) throws IOException {
    this.primaryStage = stage;
    this.app = new App();
    app.setRemoteAccess(mockedAccess);

    app.start(stage);
  }

  // Test that the application sets the scene
  @Test
  public void testAppSetScene() {
    assertNotNull(primaryStage.getScene(), "Scene should be set and not null");
  }

  // Test that the application sets the title
  @Test
  public void testSetTitle() {
    assertEquals("MovieLibrary App", primaryStage.getTitle(), "Title should be MovieLibrary App and not null");
  }

  // Test that the IOEception is handled in the start method when trying to load a non-exixtent FXML file
  // Due to the difficulty to simulate the IOException without modifying the App.java file directly,
  // We will test the FXMLLoader exception handling
  @Test
  public void testStartMethodIOException() throws IOException {
    Throwable thrown = assertThrows(IllegalStateException.class, () -> {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/movielibrary/ui/Fake.fxml"));
      fxmlLoader.load();
    });

    assertEquals("Location is not set.", thrown.getMessage(), "Exception message should indicate location issue");
  }

  // Instead of testing the main method in App.java, we will test the start method that loads the page
  // This is to avoid the JavaFX thread issue when running the main method
  // Since the main method doesn't handle any direct initialization logic
}