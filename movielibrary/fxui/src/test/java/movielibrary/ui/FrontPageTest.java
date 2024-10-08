package movielibrary.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
import static org.testfx.api.FxAssert.verifyThat;
import java.io.IOException;


public class FrontPageTest extends ApplicationTest {

    private Parent root;
    private FrontPageController controller;


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
        clickOn("#movieScrollBar");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("Loverboy");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#movieScrollBar", (ChoiceBox<String> choiceBox) -> 
        "Loverboy".equals(choiceBox.getValue()));

    }


    // Tests the "More Info" button if no movie is selected
    @Test
    @DisplayName("More info button test - no movie selected")
    public void moreInfoBtn_NoMovieSelected() throws IOException {
        clickOn("#moreInfobtn");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat(".alert", NodeMatchers.isVisible());
        verifyThat(".alert .content", hasText("Please choose a movie from the scrollbar menu"));
    }

    // Tests to see if the Movie Title, Movie Description and Movie Durations is handled correctly.
    @Test
    @DisplayName("More info button test")
      public void testMoreInfobtn() throws IOException {
        clickOn("#movieScrollBar");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("Loverboy");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#moreInfobtn");
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

    // Indirectly test the error when running the loadPage method with a invalid FXML file
    @Test
    @DisplayName("loadPage method error handling test")
      public void testLoadPage() {
        assertThrows(IllegalStateException.class, () -> {
            controller.loadPage("Fake.fxml", "Fake movie title", "Fake movie description", 100.0);
        });
  }
}
