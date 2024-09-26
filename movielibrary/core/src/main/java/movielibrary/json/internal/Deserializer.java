package movielibrary.json.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import movielibrary.core.Movie;

public class Deserializer {
    private ObjectMapper movieLibrary;
    private File file; 
    private List<Movie> moviesInLibrary;
    
    //JSON string til data, lese fil 
    public Deserializer(File file) throws IOException{
        movieLibrary = new ObjectMapper();
        this.file = file;
        moviesInLibrary = movieLibrary.readValue(this.file, new TypeReference<List<Movie>>(){});
    }

    public Movie findMovie(String title) throws IOException{
        Movie foundMovie = moviesInLibrary.stream()
                                        .filter(selectedMovie -> selectedMovie.getTitle().trim().equals(title.trim()))
                                        .findFirst()
                                        .orElse(null);
        System.out.println(foundMovie.getTitle());
        return foundMovie;
    }

    //En metode som getMovies in library list som returnerer en liste, 
    public List<Movie> getMoviesInLibrary() throws IOException{
        return this.moviesInLibrary;
    }

    //En metode som sjekker om den er false eller true
    public boolean checkIfRented(String title) throws IOException{
        Movie selectedMovie = findMovie(title);
        if(selectedMovie == null){
            throw new NoSuchElementException("The movie doesn't exist in the library.");
        }
        System.out.println(selectedMovie.getIsRented());
        return selectedMovie.getIsRented();
    }

}
