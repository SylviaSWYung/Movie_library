package movielibrary.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import movielibrary.json.internal.MovieSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class AddMoviePageTest extends ApplicationTest {
  
  private AddMoviePageController addMoviePageController;
  private Parent root;

  private MovieSerializer movieSerializer;
  private File temporaryFile;

  //deletes the temporaryFile after each test run
  @AfterEach
  public void deleteTemporaryFile() {
    temporaryFile.delete();
  }

  // Loads the FrontPage.fxml file
  @Override
  public void start(Stage stage) throws Exception {

    File sourceOfFile = new File("../core/src/main/resources/movielibrary/json/internal/moviesTest.json");
    temporaryFile = new File("../core/src/main/resources/movielibrary/json/internal/tempmovies.json");
    Files.copy(sourceOfFile.toPath(), temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

    movieSerializer = new MovieSerializer(temporaryFile);

    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("AddMoviePage.fxml"));
    root = fxmlLoader.load();    
    addMoviePageController = fxmlLoader.getController();

    addMoviePageController.setMovieFile(temporaryFile);

    stage.setScene(new Scene(root));
    stage.show();
  }

  //test if the controller is not null
  @Test
  @Order(1)
  @DisplayName("Test controller")
  public void testAddMoviePageController() {
    assertNotNull(this.addMoviePageController);
  }

  @Test
  @Order(2)
  @DisplayName("Test if nodes is shown")
  public void testNodesShownInWindow() {
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
    Button addMoviebtn = lookup("#addMoviebtn").queryAs(Button.class);
    assertThat(addMoviebtn).hasText("Add Movie");

    verifyThat("#cancelbtn", NodeMatchers.isVisible());
    Button cancelbtn = lookup("#cancelbtn").queryAs(Button.class);
    assertThat(cancelbtn).hasText("Cancel");
  }

  //test invalid inputs for title
  @Test
  @Order(3)
  @DisplayName("Test invalid title")
  public void testInvalidTitle() {
    TextField newMovieLength = (TextField) lookup("#newMovieLength").query();
    TextArea newMovieDescription = (TextArea) lookup("#newMovieDescription").query();

    clickOn("#addMoviebtn");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("You must fill out the title of the new move!"));
    clickOn("OK");
    WaitForAsyncUtils.waitForFxEvents();

    newMovieLength.setText("90");
    newMovieDescription.setText("This is about a boy who loves so much");
    clickOn("#newMovieTitle").write("Loverboy");
    clickOn("#addMoviebtn");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("The movie title already exists in the movielibrary!"));
  }

  //test invalid inputs for movie length
  @Test
  @Order(4)
  @DisplayName("Test invalid movie length")
  public void testInvalidMovieLength() {
    TextField newMovieTitle = (TextField) lookup("#newMovieTitle").query();
    TextArea newMovieDescription = (TextArea) lookup("#newMovieDescription").query();

    newMovieTitle.setText("MorningBird");

    clickOn("#addMoviebtn");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("You must fill out the length of the new movie!"));
    clickOn("OK");

    newMovieDescription.setText("This is a movie about a bird who likes to wake up early");

    clickOn("#newMovieLength").write("0");
    clickOn("#addMoviebtn");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Movie length must be greater than 0 minutes."));
    clickOn("OK");

    doubleClickOn("#newMovieLength");
    push(KeyCode.DELETE);
    clickOn("#newMovieLength").write("123");
    clickOn("#addMoviebtn");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("The movie length can't exceed 120 minutes."));
  }

  //test invalid inputs for description
  @Test
  @Order(5)
  @DisplayName("Test invalid description")
  public void testInvalidDescription() {
    TextField newMovieTitle = (TextField) lookup("#newMovieTitle").query();
    TextField newMovieLength = (TextField) lookup("#newMovieLength").query();
    TextArea newMovieDescription = (TextArea) lookup("#newMovieDescription").query();

    newMovieTitle.setText("MorningBird");
    newMovieLength.setText("100");

    clickOn("#addMoviebtn");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("You must fill out the description of the new movie!"));
    clickOn("OK");

    clickOn("#newMovieDescription").write("A bird");
    clickOn("#addMoviebtn");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("The description must be between 20 and 250 characters"));
    clickOn("OK");

    newMovieDescription.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut tristique ut nulla at pretium. Nullam malesuada dapibus purus, id sodales turpis gravida id. In hac habitasse platea dictumst. Maecenas vitae pulvinar nibh. Morbi imperdiet tortor tellus, non efficitur enim iaculis eget. Vestibulum justo arcu, dapibus faucibus luctus vel, blandit et justo. Vestibulum non dignissim arcu, ac egestas tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce id finibus augue, non mattis ipsum. Nullam ornare massa id nisl varius tincidunt. Fusce euismod augue at odio gravida pulvinar sed vel massa. Curabitur auctor gravida nunc, id mattis augue.");

    clickOn("#addMoviebtn");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("The description must be between 20 and 250 characters"));
  }

  //test addMovie method and if the movie is made with valid inputs
  @Test
  @Order(6)
  @DisplayName("Test successful added movie to library")
  public void testSuccessfulAdd() throws IOException {
    clickOn("#newMovieTitle").write("MorningBird");
    clickOn("#newMovieLength").write("100");
    clickOn("#newMovieDescription").write("This is a movie about a bird who likes to wake up early");

    clickOn("#addMoviebtn");
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("New movie is added to the library!"));
    clickOn("OK");
    WaitForAsyncUtils.waitForFxEvents();

    assertTrue(movieSerializer.movieIsFound("MorningBird"));
  }

  // test the IO exception that occurs when failing to return to the front page
  @Test
  @Order(7)
  public void testReturnToFrontPageFail() throws IOException {
    addMoviePageController.loadFrontPage(true);
    WaitForAsyncUtils.waitForFxEvents();

    verifyThat(".alert", NodeMatchers.isVisible());
    verifyThat(".alert .content", hasText("Could not load the page"));
  }

}
