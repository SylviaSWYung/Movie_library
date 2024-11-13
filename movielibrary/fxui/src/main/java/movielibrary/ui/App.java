package movielibrary.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App class initializes the application and starts the project.
 */

public class App extends Application {

  /**
   * Rest API object used for calling backend application.
   */
  private RemoteMovieLibraryAccess access = new RemoteMovieLibraryAccess();
    
  /**
   * Helper method used by tests needing to run headless.
   */
  public static void supportHeadless() {
    if (Boolean.getBoolean("headless")) {
      System.setProperty("testfx.robot", "glass");
      System.setProperty("testfx.headless", "true");
      System.setProperty("prism.order", "sw");
      System.setProperty("prism.text", "t2k");
      System.setProperty("java.awt.headless", "true");
    }
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    try {
      FXMLLoader fxmlLoader = 
          new FXMLLoader(App.class.getResource("/movielibrary/ui/FrontPage.fxml"));
      Parent parent = fxmlLoader.load();

      //Retreieves controller for FrontPage
      FrontPageController frontPageController = fxmlLoader.getController();
      frontPageController.initializes(this.access);
      
      primaryStage.setScene(new Scene(parent));
      primaryStage.setTitle("MovieLibrary App");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the remote access object for interacting with the remote movie library service.
   *
   * @param access The {@link RemoteMovieLibraryAccess} object used for interacting 
   *                with the remote library.
   */
  public void setRemoteAccess(final RemoteMovieLibraryAccess access) {
    this.access = access;
  }

  public static void main(String[] args) {
    launch();
  }
}
