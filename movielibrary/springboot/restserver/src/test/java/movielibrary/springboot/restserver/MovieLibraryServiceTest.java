package movielibrary.springboot.restserver;

import movielibrary.core.Movie;
import movielibrary.json.internal.MovieDeserializer;
import movielibrary.json.internal.MovieManager;
import movielibrary.json.internal.MovieSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code MovieLibraryServiceTest} class tests the {@link MovieLibraryService} class
 * by mocking the logic from core: {@link movieManager}, {@link movieDeserializer} and {@link movieSerializer}.
 */
public class MovieLibraryServiceTest {

    @Mock
    private MovieManager movieManager;

    @Mock
    private MovieDeserializer movieDeserializer;

    @Mock
    private MovieSerializer movieSerializer;

    @InjectMocks
    private MovieLibraryService movieLibraryService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    // Tests the getMovies method
    @Test
    public void testGetMovies() throws IOException {
        List<Movie> mockMovies = Arrays.asList(new Movie("Moana2", 120, "Under the sea boda boda"));
        doNothing().when(movieDeserializer).reloadMovieData();
        when(movieDeserializer.getMoviesInLibrary()).thenReturn(mockMovies);

        List<Movie> movies = movieLibraryService.getMovies();
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("Moana2", movies.get(0).getTitle());
    }

    // Tests the findMovie method
    @Test
    public void testFindMovie() throws IOException {
        Movie mockMovie = new Movie("Moana2", 120, "Under the sea boda boda");
        when(movieDeserializer.findMovie("Moana2")).thenReturn(mockMovie);

        Movie movie = movieLibraryService.findMovie("Moana2");
        assertNotNull(movie);
        assertEquals("Moana2", movie.getTitle());
    }

    // Tests the lendMovie method
    @Test
    public void testLendMovie() throws IOException {
        movieLibraryService.lendMovie("Moana2");

        verify(movieManager, times(1)).lend("Moana2");
    }

    // Tests the returnMovie method
    @Test
    public void testReturnMovie() throws IOException {
        movieLibraryService.returnMovie("Moana2");

        verify(movieManager, times(1)).returnBack("Moana2");
    }

    // Tests the getLentStatus method
    @Test
    public void testGetLentStatus() throws IOException {
        when(movieSerializer.getLentStatus("Moana2")).thenReturn(true);

        boolean lentStatus = movieLibraryService.getLentStatus("Moana2");
        assertTrue(lentStatus);
    }

    // Tests the addMovie method
    @Test
    public void testAddMovie() throws IOException {
        movieLibraryService.addMovie("Moana2", 120, "Under the sea boda boda");

        verify(movieManager, times(1)).addMovie("Moana2", 120, "Under the sea boda boda");
    }

    // Tests the deleteMovie method
    @Test
    public void testDeleteMovie() throws IOException {
        movieLibraryService.deleteMovie("Moana2");

        verify(movieManager, times(1)).deleteMovie("Moana2");
    }
}
