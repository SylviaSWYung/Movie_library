package movielibrary.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {

  //Movie-object for testing
  private Movie movie;

  //Default setup for each test
  @BeforeEach
  public void setup() {
    movie = Movie.createMovie("Cinderella", 110, "A girl with evil step sisters.");
  }
  
  //Testing the construction of Movie class
  @Test
  @DisplayName("Constructor")
  public void testConstructor() {
    Assertions.assertEquals("Cinderella", movie.getTitle());
    Assertions.assertEquals(110, movie.getMovieLength());
    Assertions.assertEquals("A girl with evil step sisters.", movie.getDescription());
    Assertions.assertFalse(movie.getIsLent());

    movie = Movie.createMovie("Ompalompa", 20, "ompalompalompalompalompalomp");
    Assertions.assertEquals("Ompalompa", movie.getTitle());
    Assertions.assertEquals(20, movie.getMovieLength());
    Assertions.assertEquals("ompalompalompalompalompalomp", movie.getDescription());
    Assertions.assertFalse(movie.getIsLent());

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie = Movie.createMovie("", 20, "Nothing is here at the moment!");
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie = Movie.createMovie("Hello", 20, "");
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie = Movie.createMovie("No time movie?", 0, "Nothing is here at the moment!");
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie = Movie.createMovie("Too long movie", 150, "Nothing is here at the moment!");
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      movie = Movie.createMovie("Too short movie", 150, "Nothing!");
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie = Movie.createMovie("Too long description", 20, "Sed iaculis luctus risus, in auctor magna porta nec. Etiam et diam luctus, hendrerit orci eget, lobortis urna. Morbi convallis scelerisque lectus id dapibus. Nulla sollicitudin lorem justo, non viverra tellus maximus vel. Suspendisse fermentum ultrices est, in aliquam nunc imperdiet ac. Mauris aliquam massa non eros maximus, luctus vestibulum nulla dapibus. Cras nec nunc eu dui sollicitudin laoreet. Vestibulum nunc tortor, ornare at aliquet vitae, semper vitae nibh. Proin auctor ex id nisl tempor tincidunt. Duis at elit eget diam pretium posuere eu in ante. Duis non diam dui. Mauris id vestibulum purus. In quis vulputate nibh, dignissim dictum urna. Vivamus volutpat molestie nunc, ut facilisis turpis suscipit sed. Donec maximus convallis diam, in consequat velit hendrerit pellentesque. Maecenas non ex et felis bibendum pulvinar.Sed iaculis luctus risus, in auctor magna porta nec. Etiam et diam luctus, hendrerit orci eget, lobortis urna. Morbi convallis scelerisque lectus id dapibus. Nulla sollicitudin lorem justo, non viverra tellus maximus vel. Suspendisse fermentum ultrices est, in aliquam nunc imperdiet ac. Mauris aliquam massa non eros maximus, luctus vestibulum nulla dapibus. Cras nec nunc eu dui sollicitudin laoreet. Vestibulum nunc tortor, ornare at aliquet vitae, semper vitae nibh. Proin auctor ex id nisl tempor tincidunt. Duis at elit eget diam pretium posuere eu in ante. Duis non diam dui. Mauris id vestibulum purus. In quis vulputate nibh, dignissim dictum urna. Vivamus volutpat molestie nunc, ut facilisis turpis suscipit sed. Donec maximus convallis diam, in consequat velit hendrerit pellentesque. Maecenas non ex et felis bibendum pulvinar.Sed iaculis luctus risus, in auctor magna porta nec. Etiam et diam luctus, hendrerit orci eget, lobortis urna. Morbi convallis scelerisque lectus id dapibus. Nulla sollicitudin lorem justo, non viverra tellus maximus vel. Suspendisse fermentum ultrices est, in aliquam nunc imperdiet ac. Mauris aliquam massa non eros maximus, luctus vestibulum nulla dapibus. Cras nec nunc eu dui sollicitudin laoreet. Vestibulum nunc tortor, ornare at aliquet vitae, semper vitae nibh. Proin auctor ex id nisl tempor tincidunt. Duis at elit eget diam pretium posuere eu in ante. Duis non diam dui. Mauris id vestibulum purus. In quis vulputate nibh, dignissim dictum urna. Vivamus volutpat molestie nunc, ut facilisis turpis suscipit sed. Donec maximus convallis diam, in consequat velit hendrerit pellentesque. Maecenas non ex et felis bibendum pulvinar.Sed iaculis luctus risus, in auctor magna porta nec. Etiam et diam luctus, hendrerit orci eget, lobortis urna. Morbi convallis scelerisque lectus id dapibus. Nulla sollicitudin lorem justo, non viverra tellus maximus vel. Suspendisse fermentum ultrices est, in aliquam nunc imperdiet ac. Mauris aliquam massa non eros maximus, luctus vestibulum nulla dapibus. Cras nec nunc eu dui sollicitudin laoreet. Vestibulum nunc tortor, ornare at aliquet vitae, semper vitae nibh. Proin auctor ex id nisl tempor tincidunt. Duis at elit eget diam pretium posuere eu in ante. Duis non diam dui. Mauris id vestibulum purus. In quis vulputate nibh, dignissim dictum urna. Vivamus volutpat molestie nunc, ut facilisis turpis suscipit sed. Donec maximus convallis diam, in consequat velit hendrerit pellentesque. Maecenas non ex et felis bibendum pulvinar.");
    });
  }

  //Testing the setTitle method
  @Test
  @DisplayName("Set-title")
  public void testTitle() {
    movie.setTitle("The Frog Princess");
    Assertions.assertEquals("The Frog Princess", movie.getTitle());

    movie.setLent(true);
    Assertions.assertTrue(movie.getIsLent());

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie.setTitle("");
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie.setTitle(null);
    });
  }

  //Testing the setDescription method
  @Test
  @DisplayName("Set-description")
  public void testDescription() {
    movie.setDescription("A girl married a frog. ");
    Assertions.assertEquals("A girl married a frog. ", movie.getDescription());

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie.setDescription(null);
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie.setDescription("");
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      movie.setDescription("Nothing!");
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie.setDescription("Sed iaculis luctus risus, in auctor magna porta nec. Etiam et diam luctus, hendrerit orci eget, lobortis urna. Morbi convallis scelerisque lectus id dapibus. Nulla sollicitudin lorem justo, non viverra tellus maximus vel. Suspendisse fermentum ultrices est, in aliquam nunc imperdiet ac. Mauris aliquam massa non eros maximus, luctus vestibulum nulla dapibus. Cras nec nunc eu dui sollicitudin laoreet. Vestibulum nunc tortor, ornare at aliquet vitae, semper vitae nibh. Proin auctor ex id nisl tempor tincidunt. Duis at elit eget diam pretium posuere eu in ante. Duis non diam dui. Mauris id vestibulum purus. In quis vulputate nibh, dignissim dictum urna. Vivamus volutpat molestie nunc, ut facilisis turpis suscipit sed. Donec maximus convallis diam, in consequat velit hendrerit pellentesque. Maecenas non ex et felis bibendum pulvinar.Sed iaculis luctus risus, in auctor magna porta nec. Etiam et diam luctus, hendrerit orci eget, lobortis urna. Morbi convallis scelerisque lectus id dapibus. Nulla sollicitudin lorem justo, non viverra tellus maximus vel. Suspendisse fermentum ultrices est, in aliquam nunc imperdiet ac. Mauris aliquam massa non eros maximus, luctus vestibulum nulla dapibus. Cras nec nunc eu dui sollicitudin laoreet. Vestibulum nunc tortor, ornare at aliquet vitae, semper vitae nibh. Proin auctor ex id nisl tempor tincidunt. Duis at elit eget diam pretium posuere eu in ante. Duis non diam dui. Mauris id vestibulum purus. In quis vulputate nibh, dignissim dictum urna. Vivamus volutpat molestie nunc, ut facilisis turpis suscipit sed. Donec maximus convallis diam, in consequat velit hendrerit pellentesque. Maecenas non ex et felis bibendum pulvinar.");
    });
  }

  //Testing the setMovieLength method
  @Test
  @DisplayName("Set-movieLength")
  public void testMovieLength() {
    movie.setMovieLength(90);
    Assertions.assertEquals(90, movie.getMovieLength());

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie.setMovieLength(-2);
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        movie.setMovieLength(200);
    });
  }

  //Testing the setLent method
  @Test
  @DisplayName("Test-Lent")
  public void testIsLent() {
    Assertions.assertFalse(movie.getIsLent());
    movie.setLent(true);
    Assertions.assertTrue(movie.getIsLent());
  }

}
