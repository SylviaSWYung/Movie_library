# Movielibrary 

## About the app
Movie Library is an app designed to help librarians maintain an organized structure for managing movie lending. It functions as both a self-checkout and self-check-in system for users borrowing and returning movies. The app keeps track of which movies are currently lent out by referencing a `movies.json` file, which indicates the availability of each movie for lending.

## User Stories (US)

### US 1 - Borrow Movie
As a librarian, I want users to be able to borrow a movie easily so that the lending process is streamlined and quick, reducing the workload and shortening the queue. 

<b>Key Features:</b>
* A clear "Lend" button on the Moviepage. 
* Integration with a CSV file to track the borrowing status of each movie. 

<b>Key Actions:</b>
* The user clicks the "lend" button to borrow a movie. 
* The system checks tha availability of the movie. 
* The system marks the movie as unavailable, by using boolean `true`.

### US 2 - Return Movie
As a librarian, I want users to be able to return a movie efficiently, allowing me to update the system promptly and keep the inventory accurate.

<b>Key Features:</b>
* A clear "Return" or "Cancel" button to return a movie. 
* System updates the CSV file to mark the movie as available again. 
* Automatically refresh the list to make the returned movie available for borrowing. 

<b>Key Actions:</b>
* The user clicks the "Cancel" button to return the movie.  
* The system updates the movie's status in the CSV file as available, with boolean `false`. 
* The movie is now displayed as available in the movie list. 

### US 3 - View Movie Description 
As a librarian, I want users to view detailed descriptions of movies before borrowing, so they can make informed decisions without needing additional assistance.

<b>Key Features:</b>
* A "More Info" button on the front page to view the movie details. 
* Display a separate page with the movie's title, description and other details. 
* Option to borrow the movie directly from the description page. 
* A "cancel" button to return to the movie list. 

<b>Key Actions:</b>
* The user clicks the "More Info" button to view the movie description. 
* The system displays the Moviepage with detailed information. 
* The user can either decide to borrow the movie or return to the front page. 

## Links
More info and screenshot for the app can be accessed at: <br>
[Release1 md](/docs/release1/release1.md)
