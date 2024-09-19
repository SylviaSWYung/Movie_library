# Movie Library 
[open in Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2024/gr2403/gr2403)

## Overview 
The Movie Library application is designed to assist librarians in maintaining an organized system for managing movie rentals. It serves as both a self-checkout and self-check-in platform for users who wish to borrow or return movies. The app tracks movie rentals by referencing a `movie.csv` file, which indicates the availability of each movie.

The Frontpage is designed to provide an overview of all available movies for lending, presented in a down scrollable list. Users can select a movie they’re interested in and click the "More Info" button, which takes them to the Moviepage. The Moviepage offers detailed information about the selected film, including a brief summary. At the bottom of the page, there are two buttons: "Lend" and "Cancel." By clicking "Lend", the movie will be lent to the user. "Return" button will let the user return its movie back to the library. Clicking "Cancel" will return the user to the Frontpage.

## Building and running the project 
The project is built and run using Maven. Follow these steps to start the application: 
1. Open your terminal and run `mvn install` from root directory (movielibrary-folder). 
    - This would run all of the test and qualitychecks.  
2. Ensure that you are on correct directory using `cd movielibrary/`.
3. Run `mvn clean` from the <em>gr2403/movielibrary</em>
4. Run `mvn compile` from the same directory.
5. Finally, run `mvn javafx:run` to launch the application. 

## Requirements
* <b>Java 17:</b> This project is developed using Java 17. 
* <b>Maven 3.9.9:</b> The project is managed using Maven version 3.9.9.

## Dependencies
* <b>JUnit 5</b>: These are used during the testing phase of the project. They help with unit testing, integration testing, and test framework setups. 
* <b>JavaFX 17</b>: These are libraries for building graphical user interfaces (GUIs) in JavaFX. They are included at compile-time, meaning they are required to compile and run the application. 
* <b>TestFX 4.0.16-alpha</b>: These libraries are focused on GUI testing and assertion support of the app. 
<!-- * <b></b> -->

## Directory Structure 
The docs folder contains documentation for releases, as well as images folder for the different screenshot of the app. Movielibrary folder contains the project code, including classes, tests and fxml files. Below is an illustration of the directory structure. This will likely be changed through the project phases. 

* docs
    * images
* movielibrary 
    * src
        * main 
            * java
            * resources 
        * test
    * target

<!-- ## Code quality  -->

<!-- ## Core module  -->

<!-- ## FXUI module  -->

<!-- Spring Boot module here  -->


## Links to documentation 
[Release 1 documentation](/docs/release1.md)
[User stories](/movielibrary/src/main/java/readme.md)


