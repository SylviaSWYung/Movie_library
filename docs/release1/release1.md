# Release 1 documentation 

## Milestones Reached 
In this first release, we have developed all necessary Java classes, including `Movie.java` and `MovieManager.java`, along with the required FXML documents, controllers, Maven setup and Eclipse configuration. The primary objective for this release was to establish a robust foundation infrastrucutre to facilitate smoother and more efficient subsequent development.<br>

This release also emphasizes comprehensive Javadoc documentation and the effective use of GitHub features such as issues, milestones, labels and merge request. Both `Movie.java` and `MovieManager.java` include accompanying test classes to validate their functionalities.<br>

Additionally, significant attention has been given to validation, particularly within `Movie.java`. We have successfully implemented file handling, allowing `MovieManager.java` to process a `Movie.csv` file when a movie is being borrowed. If the film is available (indicated by a false value), the librarian can register the lending by clicking "Lend" `MovieManager.java` will then update the `Movie.csv` file, changing the status of the specific film from false to true, where true represents the boolean value for `isLent()`. The impact of these implementation decisions, both positive and negative, would likely become more evident as the project progresses. <br>

## User Stories 
The user stories we aimed to fulfill involved enabling a librarian to manage the lending process for customers wishing to borrow specific movies from the library. Given that these movies are special editions, the selection is intentionally limited. This task has been successfully completed.<br>

However, there are several aspects we plan to address in future updates:<br>

* **Button Functionality**: The "Lend" button is currently always clickable. We intend to implement a feature that disables this button if the movie has already been lent to another user.<br>
* **Movie Selection**: The current selection includes only two movies, which is insufficient for variety. We aim to add 2 more movies to enhance the selection for users.<br>
* **More methods in MovieManager.java**: We plan to add `addMovie()` method in `MovieManager.java`, and subsequently expand the test coverage by adding more test in `MoviemanagerTest.java` once these features are in place. <br>
* **Update correct movies.csv file**: Currently the movie.csv file is only updated on the target folder. We wish to implement this on movie.csv file in the movielibrary folder and not the target folder. 

These improvements will enchance the user experience and functionality of the system. <br>

## Experiences 
Our plan was to hold meetings twice a week, with the possibility of additional meetings as needed. For this release, we conducted four meetings to advance the project. To ensure smooth progress, we used the Messenger app for efficient communication.<br>

Team members were expected to announce on Messenger when they completed their tasks. This practice allowed for the efficient allocation of additional work to those who were available, enhancing overall productivity. If someone had less workload or finished their tasks, they communicated this through Messenger to take on more tasks.<br>

We also recognized the importance of active engagement with Git for effective teamwork. When a merge request was active, an available team member would review it promptly. Active merge requests were announced in the Messenger group to prevent an overwhelming backlog of requests and maintain workflow efficiency.<br>

Following the release 1, we have gained a clearer understanding of the group's varying coding skill levels and a better sense of how we collaborate as a team. As a result, we've decided to incorporate pair programming from the outset in the next release. <br>

These practices significantly contributed to making the programming and development process more efficient.<br>

## App illustration 
![frontpage v1](/docs/images/frontpage-v1.png) <br>
**Figure 1: Front page v1 for release 1.** The design logic includes a drop-down menu, allowing users to select the movie they wish to borrow. By clicking the "More Info" button, users will be directed to the movie page for additional details about the selected movie. If no movie is selected, an alert will appear. 
<br>

![moviepage v1](/docs/images/moviepage-v1.png)<br>
**Figure 2: Movie page v1 for release 1.** The upper container displays the title of the selected movie, the middle container shows the movie's description, and the lower container provides the movie's runtime. By clicking the "Lend" button, the movie will become unavailable as the user has borrowed it. The "Return" button allows the user to return the movie, and clicking the "Cancel" button takes the user back to the front page to select a new movie.

## Links 
Below are links to a couple readme files describing the application and the content: <br>
[Description of app content](../readme.md)<br>
