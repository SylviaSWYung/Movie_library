package movielibrary.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import java.io.IOException;


public class FrontPageTest extends ApplicationTest {

    private Parent root;

    

    // Loads the FrontPage.fxml file
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("FrontPage.fxml"));
        root = fxmlLoader.load();    

        stage.setScene(new Scene(root));
        stage.show();
    }

    // Tests to see if the choosen movie is handled correctly
    @Test
    public void testChooseMovie() {
        clickOn("#MovieScrollBar");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("Loverboy");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#MovieScrollBar", (ChoiceBox<String> choiceBox) -> 
        "Loverboy".equals(choiceBox.getValue()));

    }


    // Tests the "More Info" button if no movie is selected
    // @Test
    // @DisplayName("More info button test - no movie selected")
    // public void moreInfoBtn_NoMovieSelected() throws IOException {
    //     clickOn("#MoreInfobtn");
    //     WaitForAsyncUtils.waitForFxEvents();

    //     verifyThat(".alert", NodeMatchers.isVisible());
    //     verifyThat(".alert .content", hasText("Please choose a movie from the scrollbar menu"));
    // }

    // Tests to see if the Movie Title, Movie Description and Movie Durations is handled correctly.
    @Test
    @DisplayName("More info button test")
    public void testMoreInfobtn() throws IOException {
        clickOn("#MovieScrollBar");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("Loverboy");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#MoreInfobtn");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#MovieTitle", NodeMatchers.isVisible());
        TextField movieTitleField = lookup("#MovieTitle").queryAs(TextField.class);
        assertThat(movieTitleField).hasText("Loverboy");

        verifyThat("#Summary", NodeMatchers.isVisible());
        TextArea movieSummaryField = lookup("#Summary").queryAs(TextArea.class);
        assertThat(movieSummaryField).hasText("Based on a true story, about a boy who marries his crush");

        verifyThat("#MovieDuration", NodeMatchers.isVisible());
        TextField movieDurationField = lookup("#MovieDuration").queryAs(TextField.class);
        assertThat(movieDurationField).hasText("30.0");
    }


}
