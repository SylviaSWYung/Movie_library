# Movie Library 
[open in Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2024/gr2403/gr2403)

## Overview 
The Movie Library application is designed to assist librarians in maintaining an organized system for managing movie lending. It serves as both a self-checkout and self-check-in platform for users who wish to borrow or return movies. The app tracks movie lending by referencing a `movies.json` file, which indicates the availability of each movie.

The Frontpage is designed to provide an overview of all available movies for lending, presented in a down scrollable list. Users can select a movie they’re interested in and click the "More Info" button, which takes them to the Moviepage. The Moviepage offers detailed information about the selected film, including a brief summary. At the bottom of the page, there are two buttons: "Lend" and "Cancel." By clicking "Lend", the movie will be lent to the user. "Return" button will let the user return its movie back to the library. Clicking "Cancel" will return the user to the Frontpage.

## Building and running the project 
The project is built and run using Maven. Follow these steps to start the application: 
1. Open your terminal and run `mvn install clean` from `gr2403/movielibrary`. 
    - This would run all of the test and qualitychecks.  
2. Ensure that you are on correct directory using `cd movielibrary/fxui`.
3. Run `mvn javafx:run` to launch the application. 

The application is now launched, and the user can try the different functionalities.<br>

### To run the test: 
1. Run `mvn clean test` from `gr2403/movielibrary`. 

## Code quality
We used JaCoCo, SpotBugs and Checkstyle for code quality check, as metioned in [Release2.md](/docs/release2/release2.md). 

Before checking the code quality, you have to do the following steps in [Building and running the project](#building-and-running-the-project). 

<!-- Write more her!! before release2 TODO -->

## Requirements
* <b>Java 17:</b> This project is developed using Java 17. 
<!-- Må kanskje oppdateres etter å ha fikset warning -->
* <b>Maven 3.8.1+:</b> The project is managed with Maven. 

## Dependencies
* <b>JUnit 5.10.0</b>: Used during the testing phase of the project. It helps with unit testing, integration testing, and test framework setups. 
* <b>JavaFX 17</b>: Used to provide libraries for building graphical user interfaces (GUIs) in JavaFX. They are required to compile and run the application. 
* <b>TestFX 4.0.16-alpha</b>: Used for GUI testing and assertion support in application. 
* <b>CheckStyle 3.5.0</b>: Used to ensure code style compliance by analyzing Java code for adherence to coding conventions. 
* <b>SpotBugs 4.8.6.4</b>: Used for static code analysis to identify potential bugs in the codebase. 
* <b>JaCoCo 0.8.12</b>: Used for measuring code coverage during testing, providing insights into which parts of the code are exercised by tests.
* <b>Maven Surfire 3.1.2</b>: Used to run tests in the Maven build lifecycle, facilitating automated test execution. 
* <b>Mockito 5.13.0</b>: Used for creating mock objects in unit tests, allowing for isolation of the code being tested. 
* <b>Jackson core 2.17.2</b>: Used for processing JSON data, providing functionality to parse and generate JSON in the application. 
* <b>Jackson Databind 2.17.2</b>: Used to convert between Java objects and JSON, simplifying data serialization and deserialization. 
* <b>WireMock 2.27.2</b>: Used to simulate HTTP services for testing, enabling the application to interact with mock server responses. 
* <b>Hamcrest 2.2</b>: Used for writing expressive and readable assertions in tests, enhancing the clarity of test conditions. 


## Directory Structure 
The docs folder contains documentation for releases, as well as images folder for the different screenshot of the app. Movielibrary folder contains the project code, including core module, and fxui module. The core and fxui module, each contains classes, and tests for the corresponding classes. The fxui also contains fxml files. While in core folder it contains the JSON file and filehandling. Below is an illustration of the directory structure. This will likely be changed through the project phases. 

* docs
    * images
    * release1
    * release2
* movielibrary 
    * core
        * src 
            * main
              * java
                * movielibrary
                  * core
                  * json\internal
              * resources\movielibrary
            * test 
        * target
    * fxui
      * src 
        * main
          * java
            * movielibrary\ui
          * resources 
        * test
      * target 

## Core module 
The Core Module contains the java classes reponsible for managing the fundamental logic of the application, along with their respective test classes. Additionally, it includes a `module-info` file and a `pom.xml`. 

### Packages 
The core module is divided into two packages, core and json. 

<b>Core</b>: <br>
<b>Filepath</b>: [/movielibrary/core/src/main/java/movielibrary/core/](/movielibrary/core/src/main/java/movielibrary/core/) <br>

Core consist of `Movie.java`. `Movie` class represents a movie with attributes such as title, length, description, and lending status. It includes methods for data validation, and retrieval of movie properties, ensuring that the input values meet specified criteria. The class encapsulates movie-related functionalities, making it a crucal component of the Movie Library application. 

<b>JSON\internal</b>: <br>
<b>Filepath</b>: [/movielibrary/core/src/main/java/movielibrary/json/internal/](/movielibrary/core/src/main/java/movielibrary/json/internal/) <br>

JSON\internal consists of `MovieDeserializer.java`, `MovieManager.java` and `MovieSerializer.java`. These three classes work together to manage a movie library's data efficiently. The `MovieDeserializer` class is responsible for deserializing JSON data from a file into a list of `Movie` objects, enabling the searching of movies by title, retrieving the complete list of movies, and checking their lending status using the Jackson library for JSON deserialization. The `MovieManager` class interacts with the `MovieSerializer` to facilitate the lending and returning of movies, ensuring that their lending status is accurately updated in the JSON file. Meanwhile, the `MovieSerializer` class handles the serialization of `Movie` objects back into the JSON format, allowing updates to the lending status and writing the entire movie list back to the file, thus ensuring the integrity and accuracy of the movie data.

### Test files

<b>Core</b>:<br>
<b>Filepath</b>: [/movielibrary/core/src/test/java/movielibrary/core/](/movielibrary/core/src/test/java/movielibrary/core/)<br>

The `MovieTest` class is designed to verify the functionality of the Movie class within the `movielibrary.core` package. Utilizing the JUnit 5 framework, it includes several test methods that assess various aspects of the `Movie` class, such as the constructor, setter methods for the title, description, and movie length, as well as the functionality of the lending feature. JaCoCo reports the test coverage as 100%.


<b>JSON</b>:<br>
<b>Filepath</b>: [/movielibrary/core/src/test/java/movielibrary/json/](/movielibrary/core/src/test/java/movielibrary/json/) <br>




## FXUI module 
The FXUI Module contains the main java class that executes the application (`App.java`), all associated FXML files that define the user interface, their corresponding controller classes, UI test files, and test versions of each FXML window. Similar to the Core Module, it also includes a `module-info` file and a `pom.xml` file.  

### FXML files

### Controllers

### Test files



<!-- Spring Boot module here  -->


## Links to documentation 
[Release 1 documentation](/docs/release1/release1.md)
[Release 2 documentation](/docs/release2/release2.md)
[User stories](/movielibrary/src/main/java/readme.md)
[AI tools](/docs/release1/ai-tools.md)


