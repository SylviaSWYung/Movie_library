# Release 1 documentation 

## Milestones Reached 
In this first release, we have developed all necessary Java classes, including `Movie.java` and `MovieManager.java`, along with the required FXML documents, controllers, Maven setup and Eclipse configuration. The primary objective for this release was to establish a robust foundation infrastrucutre to facilitate smoother and more efficient subsequent development.<br>

This release also emphasizes comprehensive Javadoc documentation and the effective use of GitHub features such as issues, milestones, labels and merge request. Both `Movie.java` and `MovieManager.java` include accompanying test classes to validate their functionalities.<br>

Additionally, significant attention has been given to validation, particularly within `Movie.java`. We have successfully implemented file handling, allowing `MovieManager.java` to process a `Movie.csv` file when a movie is being borrowed. If the film is available (indicated by a false value), the librarian can register the rental by clicking "Lend" `MovieManager.java` will then update the `Movie.csv` file, changing the status of the specific film from false to true, where true represents the boolean value for `isRented()`. The impact of these implementation decisions, both positive and negative, would likely become more evident as the project progresses. <br>

## User Story 
The user story we aimed to fulfill involved enabling a librarian to manage the lending process for customers wishing to borrow specific movies from the library. Given that these movies are special editions, the selection is intentionally limited. This task has been successfully completed.<br>

However, there are several aspects we plan to address in future updates:<br>

* **Button Functionality**: The "Lend" button is currently always clickable. We intend to implement a feature that disables this button if the movie has already been lent to another user.<br>
<!-- Er punktet over fortsatt gjeldende? -->
* **Description Length**: The current description field is limited to 250 characters. We plan to expand this limit for more characters for better detail.<br>
* **Movie Selection**: The current selection includes only two movies, which is insufficient for variety. We aim to add 2 more movies to enhance the selection for users.<br>
* **More methods in MovieManager.java**: We plan to add `addMovie()` method in `MovieManager.java`, implement a method to retrieve the list of movies within `MovieManager.java`, and subsequently expand the test coverage by adding more test in `MoviemanagerTest.java` once these features are in place. <br>

These improvements will enchance the user experience and functionality of the system. <br>

## Experiences 
Our plan was to hold meetings twice a week, with the possibility of additional meetings as needed. For this release, we conducted four meetings to advance the project. To ensure smooth progress, we used the Messenger app for efficient communication.<br>

Team members were expected to announce on Messenger when they completed their tasks. This practice allowed for the efficient allocation of additional work to those who were available, enhancing overall productivity. If someone had less workload or finished their tasks, they communicated this through Messenger to take on more tasks.<br>

We also recognized the importance of active engagement with Git for effective teamwork. When a merge request was active, an available team member would review it promptly. Active merge requests were announced in the Messenger group to prevent an overwhelming backlog of requests and maintain workflow efficiency.<br>

These practices significantly contributed to making the programming and development process more efficient.<br>

## App illustration 
![frontpage v1](/docs/images/frontpage-v1.png) <br>
**Figure 1: Front page v1 for release 1.** The design logic includes a drop-down menu, allowing users to select the movie they wish to borrow. By clicking the "More Info" button, users will be directed to the movie page for additional details about the selected movie. 
<br>

![moviepage v1](/docs/images/moviepage-v1.png)<br>
**Figure 2: Movie page v1 for release 1.** The upper container displays the title of the selected movie, the middle container shows the movie's description, and the lower container provides the movie's runtime. By clicking the "Lend" button, the movie will become unavailable as the user has borrowed it. The "Return" button allows the user to return the movie, and clicking the "Cancel" button takes the user back to the front page to select a new movie.

<!-- Se øverst button igjen -->

## Links 
Below are links to a couple readme files describing the application and the content: <br>
[Description of app content](../readme.md)<br>
