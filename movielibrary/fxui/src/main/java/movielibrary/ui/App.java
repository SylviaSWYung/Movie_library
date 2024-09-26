package movielibrary.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App class initializes the application and starts the project
 */

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/movielibrary/ui/FrontPage.fxml"));
            Parent parent = fxmlLoader.load();
            
            primaryStage.setScene(new Scene(parent));
            primaryStage.setTitle("MovieLibrary App");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
