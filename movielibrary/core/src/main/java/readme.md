# Movielibrary 

## About the app
Movie Library is an app designed to help librarians maintain an organized structure for managing movie lending. It functions as both a self-checkout and self-check-in system to assist the liberian with borrower interactions, including borrowing and returning movies. The app keeps track of which movies are currently lent out by referencing a `movies.json` file, which indicates each movie's availability.

## User Stories (US)

### US 1 - Borrow Movie
As a librarian, I want to handle movies borrowing quicly and easily so that the lending process is streamlined, reducing workload and shortening the queue. 

<b>Key Features:</b>
* A clear "Lend" button on the Moviepage. 
* Integration with a json file to track the borrowing status of each movie. 

<b>Key Actions:</b>
* The librarian clicks the "lend" button to borrow a movie. 
* The system checks tha availability of the movie. 
* The system updates the movie's availability in a json file, and marking it as unavailable using a boolean `true` value.

### US 2 - Return Movie
As a librarian, I want to be able to return a movie efficiently, allowing me to update the system promptly and keep the inventory accurate.

<b>Key Features:</b>
* A clear "Return" button to return a movie. 
* System updates the json file to mark the movie as available again. 
* Automatic refresh of the list to make the returned movie available for borrowing. 

<b>Key Actions:</b>
* The librarian clicks the "Return" button to return a movie.  
* The system updates the movie's status in the json file as available, with boolean `false`. 
* The movie is now displayed as available in the movie list. 

### US 3 - View Movie Description 
As a librarian, I want to view detailed descriptions of movies to ensure the correct movie is borrowed by the borrower.

<b>Key Features:</b>
* A "More Info" button on the front page to view the movie details. 
* Display a separate page with the movie's title, description and other details. 
* Option to borrow the movie directly from the description page. 
* A "cancel" button to return to the movie list. 

<b>Key Actions:</b>
* The librarian clicks the "More Info" button to view the movie description. 
* The system displays the Moviepage with detailed information. 
* The librarian can either decide to borrow the movie or return to the front page. 

### US 4 - Adding New Movies
As a liberian, I want to be able to add new movies to the library by specifiying essential details like the movie title, description and duration, so that the library's collection stays updated with the latest movies. 

<b>Key Features:</b>
* Ability to enter the movie's title, a brief description, and its duration in minutes. 
* Validation to ensure no required fields are left blank, and the parameters have correct values. 
* Confirmation message to verify that the movie has been successfully added to the library. 

<b>Key Actions:</b>
* Librarian selects the "Add Movie" option.
* Librarian fills in the title, description and duration fields. 
* Librarian clicks "Add" to add the new movie, receiving confirmation upon success. 

### US 5 - Deleting Movies 
As a librarian, I want to be able to delete movies from the library collection if they are no longer available or relevant, so that the library inventor remains accurate and up-to-date. 

<b>Key Features:</b>
* A delete button that removes the selected movie from the collection.
* A confirmation prompt after deleting a movie.
* Not possible to delete if there is only one movie in the library.

<b>Key Actions:</b>
* Librarian selects the delete option for the chosen movie. 



## Links
More info and screenshot for the app can be accessed at: <br>
[Release1 md](/docs/release1/release1.md) <br>
[Readme](/readme.md)
