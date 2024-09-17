package movielibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * MovieManager class contains data, and has methods to verify it
 */
public class MovieManager {
    
    private List<Movie> movies;
    private File file;
    private Scanner scanner;

    /**
     * Initializes the MoveManager object. Creates a File object using a path to the Movies.csv file.
     * Adds all movies in Movies.csv to the List movies
     */
    public MovieManager() throws IOException {
        movies = new ArrayList<Movie>();
        this.file = new File("movielibrary/src/main/resources/Movies.csv");

        this.scanner = new Scanner(this.file);
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String[] movieInfo = scanner.nextLine().split(";");

            String title = movieInfo[0];
            //double movieLength = Double.parseDouble(movieInfo[1]);
            String description = movieInfo[2];

            movies.add(new Movie(title, 2, description));
        }
        scanner.close();
    }

    /**
     * Returns the file
     * 
     * @return a File object with data for all movies in library
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Sets the file
     * The file cannot be empty and has to be of type File
     * 
     * @param file a File object with a path
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Rents a movie from library. Sets status to true in file
     * @param title a String with the title of the movie
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public void rent(String title) throws IOException {
        Movie foundMovie = findMovie(title);

        if (!checkIfRented(title)) {
            updateMovieStatus(title, true);
        }
        foundMovie.setRented(true);
    }

    /**
     * Returns movie back to library. Sets status to false in file
     * @param title a String with the title of the movie
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public void returnBack(String title) throws IOException {
        Movie foundMovie = findMovie(title);

        if (checkIfRented(title)) {
            updateMovieStatus(title, false);
        }
        foundMovie.setRented(false);
    }

    /**
     * Finds a movie in library by title. Title can't be empty.
     * 
     * @param title a String with the title of the movie
     * @return the movie with the given title
     * @throws IllegalArgumentException if the title is empty
     * @throws NoSuchElementException if the movie doesn't exist in library
     */
    public Movie findMovie(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("The movie title can't be empty. ");
        }

        Movie movieFromTitle = this.movies.stream()
                        .filter(m -> m.getTitle().equals(title))
                        .findAny()
                        .orElse(null);
        
        if (movieFromTitle == null) {
            throw new NoSuchElementException("This movie doesn't exist in this library. ");
        }

        return movieFromTitle;
    }

    /**
     * Checks the availability of the movie in file, either true or false
     * @param title a String with the title of the movie
     * @return boolean if the movie is rented or not
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public boolean checkIfRented(String title) throws IOException {
        findMovie(title);
        boolean isRented = false;

        this.scanner = new Scanner(this.file);
        while (scanner.hasNextLine()) {
            String[] newMovie = scanner.nextLine().split(";");

            String titleInFile = newMovie[0].trim();
            if (titleInFile.equals(title)) {
                isRented = Boolean.parseBoolean(newMovie[3].trim());
            }
        }

        return isRented;
    }

    /**
     * Updates the rented status of the movie
     * @param title a String with the title of the movie
     * @param newStatus a boolean to set the movie status rented/not rented
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public void updateMovieStatus(String title, boolean newStatus) throws IOException {
        findMovie(title);
        StringBuilder content = new StringBuilder();

        this.scanner = new Scanner(this.file);
        while (scanner.hasNextLine()) {
            String[] movieData = scanner.nextLine().split(";");

            String titleInFile = movieData[0].trim();
            if (title.equals(titleInFile)) {
                movieData[3] = String.valueOf(newStatus);
            }
            String updatedMovieInformation = String.join(";", movieData);
            content.append(updatedMovieInformation).append("\n");
        }
        scanner.close();
        reWriteFile(content);
    }


    /**
     * Rewrites file
     * @param builder StringBuilder used to manipulate strings
     * @throws IOException exceptions produced by failed or interrupted I/O operations
     */
    public void reWriteFile(StringBuilder builder) throws IOException {
        FileWriter writer = new FileWriter(this.file);
        writer.write(builder.toString());
        writer.close();
    }

    /**
     * toString method for object
     * @return String with titles of movies in List movies
     */
    @Override
    public String toString() {
        String output = "";
        for (Movie m : this.movies) {
            output += m.getTitle() + "\n";
        }
        return output;
    }
}