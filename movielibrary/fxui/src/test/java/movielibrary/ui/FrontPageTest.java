package movielibrary.ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import java.io.IOException;


public class FrontPageTest extends ApplicationTest {

  private Parent root;
  private FrontPageController controller;

  @BeforeAll
  public static void setUpHeadless() {
    App.supportHeadless();
  }

  // Loads the FrontPage.fxml file
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("FrontPage.fxml"));
    root = fxmlLoader.load();    

    stage.setScene(new Scene(root));
    stage.show();
  }

  // initializes the FrontPageController
  @BeforeEach
  public void setUp() {
    controller = new FrontPageController();
  }

  // Tests to see if the choosen movie is handled correctly
  @Test
  @DisplayName("Choosing a movie from the scrollbar test")
  public void testChooseMovie() {
    @SuppressWarnings("unchecked")
    ChoiceBox<String> choiceBox = lookup("#movieScrollBar").queryAs(ChoiceBox.class);
    if (choiceBox != null) {
        // Select "Loverboy" 
        interact(() -> choiceBox.getSelectionModel().select("Loverboy"));
        WaitForAsyncUtils.waitForFxEvents();

        // Verify selection 
        verifyThat("#movieScrollBar", (ChoiceBox<String> choiceBox2) -> 
        "Loverboy".equals(choiceBox2.getValue())); 
    } else {
        System.out.println("ChoiceBox with ID #movieScrollBar not found.");
    }
  }


  // Tests the "More Info" button if no movie is selected
  @Test
  @DisplayName("More info button test - no movie selected")
  public void moreInfoBtn_NoMovieSelected() throws IOException {
    Button moreInfoBtn = (Button) lookup("#moreInfobtn").query();  // Locate the button by its ID
    
    Platform.runLater(() -> {
      if (moreInfoBtn != null) {
          moreInfoBtn.fire();
          System.out.println("Button fired");
      } else {
          System.out.println("Button is null");
      }
    });

    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Please choose a movie from the scrollbar menu"));
  }

  // Tests to see if the Movie Title, Movie Description and Movie Durations is handled correctly.
  @Test
  @DisplayName("More info button test")
  public void testMoreInfobtn() throws IOException {
    @SuppressWarnings("unchecked")
    ChoiceBox<String> choiceBox = lookup("#movieScrollBar").queryAs(ChoiceBox.class);
    if (choiceBox != null) {
        // Select "Loverboy" 
        interact(() -> choiceBox.getSelectionModel().select("Loverboy"));
        WaitForAsyncUtils.waitForFxEvents();

        // Verify selection 
        verifyThat("#movieScrollBar", (ChoiceBox<String> choiceBox2) -> 
        "Loverboy".equals(choiceBox2.getValue())); 
    } else {
        System.out.println("ChoiceBox with ID #movieScrollBar not found.");
    }

    Button moreInfoBtn = (Button) lookup("#moreInfobtn").query();  // Locate the button by its ID
    
    Platform.runLater(() -> {
      if (moreInfoBtn != null) {
          moreInfoBtn.fire();
          System.out.println("Button fired");
      } else {
          System.out.println("Button is null");
      }
    });

    WaitForAsyncUtils.waitForFxEvents();

    verifyThat("#movieTitleInPage", NodeMatchers.isVisible());
    TextField movieTitleField = lookup("#movieTitleInPage").queryAs(TextField.class);
    assertThat(movieTitleField).hasText("Loverboy");

    verifyThat("#summary", NodeMatchers.isVisible());
    TextArea movieSummaryField = lookup("#summary").queryAs(TextArea.class);
    assertThat(movieSummaryField).hasText("Based on a true story, about a boy who marries his crush");

    verifyThat("#movieDuration", NodeMatchers.isVisible());
    TextField movieDurationField = lookup("#movieDuration").queryAs(TextField.class);
    assertThat(movieDurationField).hasText("30.0");
  }

  // Tests to see if AddMoveiPage.fxml is loaded correctly
  @Test
  @DisplayName("Addmovie button test")
  public void testAddMoviebtn() {
    Button addMoviebtn = (Button) lookup("#addMoviebtn").query();  // Locate the button by its ID

    Platform.runLater(() -> {
      if (addMoviebtn != null) {
          addMoviebtn.fire();
          System.out.println("Button fired");
      } else {
          System.out.println("Button is null");
      }
    });
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat("#newMovieTitle", NodeMatchers.isVisible());
    TextField newMovieTitleField = lookup("#newMovieTitle").queryAs(TextField.class);
    assertTrue(newMovieTitleField.getText().isEmpty(), "Textfield should be empty on load");

    verifyThat("#newMovieLength", NodeMatchers.isVisible());
    TextField newMovieLengthField = lookup("#newMovieLength").queryAs(TextField.class);
    assertTrue(newMovieLengthField.getText().isEmpty(), "Textfield should be empty on load");

    verifyThat("#newMovieDescription", NodeMatchers.isVisible());
    TextArea newMovieDescriptionField = lookup("#newMovieDescription").queryAs(TextArea.class);
    assertTrue(newMovieDescriptionField.getText().isEmpty(), "TextArea should be empty on load");

    verifyThat("#addMoviebtn", NodeMatchers.isVisible());
    assertThat(addMoviebtn).hasText("Add movie");

    verifyThat("#cancelbtn", NodeMatchers.isVisible());
    Button cancelbtn = lookup("#cancelbtn").queryAs(Button.class);
    assertThat(cancelbtn).hasText("Cancel");
  }

  // Indirectly test the error when running the loadPage method with a invalid FXML file
  @Test
  @DisplayName("loadPage method error handling test")
  public void testLoadPage() {
    assertThrows(IllegalStateException.class, () -> {
      controller.loadPage("Fake.fxml", "Fake movie title", "Fake movie description", 100.0);
    });
  }
}
