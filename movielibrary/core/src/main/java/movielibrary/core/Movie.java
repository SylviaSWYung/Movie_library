package movielibrary.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Movie class contains data, and has methods to verify it.
 */
public class Movie {
  private String title; 
  private double movieLength; 
  private String description; 
  private boolean isLent; //Default value is set to false. False - Available. 
  
  /**
   * Constructs a Movie object with the specified title, movie length, and description. 
   * The movie is initially available for lending ({@code isLent} set to false). 
   *
   * @param title the title of the movie, cannot be null or empty.
   * @param movieLength the length of the movie in minutes, 
   *                    must be greater than 0 and less than or equal to 120. 
   * @param description a brief description of the movie, cannot be null or empty, 
   *                    and must not exceed 250 characters. 
   */
  public Movie(@JsonProperty("title") String title, @JsonProperty("movieLength") 
      double movieLength, @JsonProperty("description") String description) {
    this.title = title; 
    this.movieLength = movieLength;
    this.description = description; 
    isLent = false; 
  }

  /**
   * Creates a Movie object after validating title, movieLength and description.
   *
   * @param title the title of the movie, cannot be null or empty.
   * @param movieLength the length of the movie in minutes, 
   *                    must be greater than 0 and less than or equal to 120. 
   * @param description a brief description of the movie, cannot be null or empty, 
   *                    and must not exceed 250 characters. 
   * @throws IllegalArgumentException if any of the parameters are invalid 
   *                                  (null, empty, or out of range)
   */
  public static Movie createMovie(String title, double movieLength, String description) {
    handleStringError(title);
    handleDoubleError(movieLength); 
    handleStringError(description);
    handleDescriptionLength(description);

    return new Movie(title, movieLength, description);
  }
  
  /**
   * Retrieves the title of the movie.
   *
   * @return the title of the movie as a String
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Sets the title of the movie. 
   * The title cannot be null or empty. 
   *
   * @param title the title of the movie
   * @throws IllegalArgumentException if the title is null or empty
   */
  public void setTitle(String title) {
    handleStringError(title);
    this.title = title;
  }

  /**
   * Retrieves the length of the movie in minutes.
   *
   * @return the movie length in double. 
   */
  public double getMovieLength() {
    return this.movieLength;
  }

  /**
   * Sets the length of the movie. 
   * The movie length must be greater than 0 and less than or equal to 120 minutes. 
   *
   * @param movieLength the length of the movie in minutes.
   * @throws IllegalArgumentException if the movie length is less than or equal 
   *                                  to 0 or greater than 120 minutes
   */
  public void setMovieLength(double movieLength) {
    handleDoubleError(movieLength);
    this.movieLength = movieLength;
  }

  /**
   * Retrieves the description of the movie.
   *
   * @return the description of the movie as a String
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the description of the movie. 
   * The description cannot be null or empty, and its length must not exceed 250 characters. 
   *
   * @param description the description of the movie. 
   * @throws IllegalArgumentException if the description is null, empty or exceeds 250 characters.  
   */
  public void setDescription(String description) {
    handleStringError(description);
    handleDescriptionLength(description);
    this.description = description;
  }

  /**
   * Retrives the lending status of the movie. 
   * If the movie is lended, it returns true. Otherwise, it returns false. 
   *
   * @return true if the the movie is lending, false if it is available. 
   */
  public boolean getIsLent() {
    return this.isLent;
  }

  /**
   * Sets the lending status of the movie. 
   *
   * @param isLent true if the movie is lended, false if it is available 
   */
  public void setLent(boolean isLent) {
    this.isLent = isLent;
  }

  /**
   * Validates the string input to ensure it is not null or empty. 
   *
   * @param input the string to validate
   * @throws IllegalArgumentException if the string is null or empty
   */
  private static void handleStringError(String input) {
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException("Your argument can't be empty or null");
    }
  }

  /**
   * Validates the movie length to ensure it is within the acceptable range.
   *
   * @param minutes the movie length in minutes
   * @throws IllegalArgumentException if the length is less than or equal to 0 or greater than 12
   */
  private static void handleDoubleError(double minutes) {
    if (minutes <= 0) {
      throw new IllegalArgumentException("Movie length must be greater than 0 minutes.");
    }
    if (minutes > 120) {
      throw new IllegalArgumentException("The movie length can't exceed 120 minutes.");
    }
  }

  /**
   * Validates the length of the given description.
   * Throws an exception if the length exceeds the maximum allowed number of characters. 
   *
   * @param description the description to validate, which is a String
   * @throws IllegalArgumentException if the length of the description isn't between 20-250 characters
   */
  private static void handleDescriptionLength(String description) {
    int maxCharacter = 250;
    int minCharacter = 20;
    if (description.length() > maxCharacter || description.length() < minCharacter) {
      throw new IllegalArgumentException("The description must be between 20 and 250 characters");
    }
  }
}