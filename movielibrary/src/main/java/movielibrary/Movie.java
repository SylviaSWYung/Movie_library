package movielibrary;

/**
 * Movie class contains data, and has methods to verify it
 */
public class Movie {
    private String title; 
    private double movieLength; 
    private String description; 
    private boolean isRented; //Default value is set to false. False - Available. 
    
    /**
     * Movie object with required parameters. Rent status is set to false. 
     * Which means that it is available.
     * 
     * @param title the title of the movie 
     * @param movieLength the play length of the movie
     * @param description description about the movie
     */
    public Movie(String title, double movieLength, String description){
        this.title = title; 
        this.movieLength = movieLength; 
        this.description = description; 
        isRented = false; 
    }
    
    /**
     * Returns the title of the movie
     * 
     * @return a String with the title of the movie
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title of the movie
     * The title of the movie can't be empty. 
     * 
     * @param title
     */
    public void setTitle(String title) {
        if(title == null){
            throw new NullPointerException("Title can't be null");
        }
        this.title = title;
    }

    /**
     * Returns the movie length.
     * 
     * @return the movie length in Double. 
     */
    public double getMovieLength() {
        return this.movieLength;
    }

    /**
     * Sets the length of the movie. 
     * It has to be more than 0 minutes, and maximum of 120min. 
     * 
     * @param movieLength
     */
    public void setMovieLength(double movieLength) {
        if(movieLength < 0){
            throw new IllegalArgumentException("The value is in minutes. The movie can't be 0 minutes.");
        }
        if(movieLength > 120){
            throw new IllegalStateException("The movie is too long.");
        }
        this.movieLength = movieLength;
    }

    /**
     * Returns a short description of the movie.
     * 
     * @return a String with the description of the movie
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the movie. 
     * The description of the movie cannot be more than 50 char and empty. 
     * 
     * @param description a String description of the movie 
     */
    public void setDescription(String description) {
        if(description == null){
            throw new NullPointerException("The description can't be null");
        }
        int maxCharacter = 50;
        if(description.length() > maxCharacter || description.length() <= 0){
            throw new IllegalStateException("The description is not valid");
        }
        this.description = description;
    }

    /**
     * Returns a boolean for if it is rented. 
     * False is equal to available. 
     * True is equal to rented, in this case not available. 
     * 
     * @return a Boolean with the rented value. 
     */
    public boolean getIsRented() {
        return this.isRented;
    }

    /**
     * Sets the status of the rented movie. 
     * False is equal to available. 
     * True is equal to rented. 
     * 
     * @param isRented a Boolean stated the movie's rented status. 
     */
    public void setRented(boolean isRented) {
        this.isRented = isRented;
    }     
}
