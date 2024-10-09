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
We utilized JaCoCo, SpotBugs and Checkstyle to ensure code quality, as metioned in [Release2.md](/docs/release2/release2.md). 

Before verifying code quality, please run the project with `mvn install clean` from `gr2403/movielibrary`. Then you can proceed to quality check the project. 

To check the JaCoCo test coverage for the Java project, open the `index.html` file. Its location depends on the module of interest. For the Core module, the file can be found at [/movielibrary/core/target/site/jacoco/](/movielibrary/core/target/site/jacoco/index.html), and for the FXUI module, the file is located at [/movielibrary/fxui/target/site/jacoco/](/movielibrary/fxui/target/site/jacoco/index.html). 

To identify bugs and bad practices in the Java project, run `mvn spotbugs:check` to analyze the SpotBugs report. It provides details for each module, including the size of BugInstance and the number of errors. 

To verify adherence to Google's coding standards, run `mvn checkstyle:check`. This will report any Checkstyle violations detected in the project. 

## Requirements
* <b>Java 17:</b> This project is developed using Java 17. 
* <b>Maven 3.11.0:</b> The project is managed with Maven. 

## Dependencies
* <b>JUnit 5.10.0</b>: Used during the testing phase of the project. It helps with unit testing, integration testing, and test framework setups. 
* <b>JavaFX 22.0.2</b>: Used to provide libraries for building graphical user interfaces (GUIs) in JavaFX. They are required to compile and run the application. 
* <b>TestFX 4.0.16-alpha</b>: Used for GUI testing and assertion support in application. 
* <b>Mockito 5.13.0</b>: Used for creating mock objects in unit tests, allowing for isolation of the code being tested. 
* <b>Jackson core 2.17.2</b>: Used for processing JSON data, providing functionality to parse and generate JSON in the application. 
* <b>Jackson Databind 2.17.2</b>: Used to convert between Java objects and JSON, simplifying data serialization and deserialization. 
* <b>Hamcrest 2.2</b>: Used for writing expressive and readable assertions in tests, enhancing the clarity of test conditions. 

## Plugins
* <b>Maven Compiler 3.11.0</b>: Used to compile Java source code into bytecode, the Maven Compiler Plugin manages the compilation process.
* <b>Maven CheckStyle 3.5.0</b>: Used to ensure code style compliance by analyzing Java code for adherence to coding conventions. 
* <b>Maven Surfire 3.1.2</b>: Used to run tests in the Maven build lifecycle, facilitating automated test execution.
* <b>SpotBugs 4.8.6.4</b>: Used for static code analysis to identify potential bugs in the codebase. 
* <b>JaCoCo 0.8.12</b>: Used for measuring code coverage during testing, providing insights into which parts of the code are exercised by tests. 
* <b>WireMock 2.27.2</b>: Used to simulate HTTP services for testing, enabling the application to interact with mock server responses. 

## Directory Structure 
The docs folder contains documentation for releases, as well as images folder for the different screenshot of the app. Movielibrary folder contains the project code, including core module, and fxui module. The core and fxui module, each contains classes, and tests for the corresponding classes. The fxui also contains fxml files. While in core folder it contains the JSON file and filehandling. Below is an illustration of the directory structure. This will likely be changed through the project phases. 

* [docs](/docs/)
    * [images](/docs/images/)
    * [release1](/docs/release1/)
    * [release2](/docs/release2/)
      * [umlDiagrams](/docs/release2/umlDiagrams/)
* [movielibrary](/movielibrary/)
    * [core](/movielibrary/core/)
      * [src](/movielibrary/core/src/)
          * [main](/movielibrary/core/src/main/)
            * [java](/movielibrary/core/src/main/java/)
              * [movielibrary](/movielibrary/core/src/main/java/movielibrary/)
                * [core](/movielibrary/core/src/main/java/movielibrary/core/)
                * [json](/movielibrary/core/src/main/java/movielibrary/json/)
              * [resources\movielibrary](/movielibrary/core/src/main/resources/movielibrary/)
          * [test](/movielibrary/core/src/test/)
        * [target](/movielibrary/core/target/)
    * [fxui](/movielibrary/fxui/)
      * [src](/movielibrary/fxui/src/)
        * [main](/movielibrary/fxui/src/main/)
          * [java](/movielibrary/fxui/src/main/java/)
            * [movielibrary\ui](/movielibrary/fxui/src/main/java/movielibrary/ui/)
          * [resources](/movielibrary/fxui/src/main/resources/)
        * [test](/movielibrary/fxui/src/test/)
      * [target](/movielibrary/fxui/target/)

## Core module 
The Core Module contains the java classes reponsible for managing the fundamental logic of the application, along with their respective test classes. Additionally, it includes a `module-info` file and a `pom.xml`. 

### Packages 
The core module is divided into two packages, core and json. 

<b>Core</b>: <br>
<b>Filepath</b>: [/movielibrary/core/src/main/java/movielibrary/core/](/movielibrary/core/src/main/java/movielibrary/core/) <br>

Core consist of `Movie.java`. `Movie` class represents a movie with attributes such as title, length, description, and lending status. It includes methods for data validation, and retrieval of movie properties, ensuring that the input values meet specified criteria. The class encapsulates movie-related functionalities, making it a crucal component of the Movie Library application. 

<b>JSON</b>: <br>
<b>Filepath</b>: [/movielibrary/core/src/main/java/movielibrary/json/](/movielibrary/core/src/main/java/movielibrary/json/internal/) <br>

JSON consists of `MovieDeserializer.java`, `MovieManager.java` and `MovieSerializer.java`. These three classes work together to manage a movie library's data efficiently. The `MovieDeserializer` class is responsible for deserializing JSON data from a file into a list of `Movie` objects, enabling the searching of movies by title, retrieving the complete list of movies, and checking their lending status using the Jackson library for JSON deserialization. The `MovieManager` class interacts with the `MovieSerializer` to facilitate the lending and returning of movies, ensuring that their lending status is accurately updated in the JSON file. Meanwhile, the `MovieSerializer` class handles the serialization of `Movie` objects back into the JSON format, allowing updates to the lending status and writing the entire movie list back to the file, thus ensuring the integrity and accuracy of the movie data.

### Test files

<b>Core</b>:<br>
<b>Filepath</b>: [/movielibrary/core/src/test/java/movielibrary/core/](/movielibrary/core/src/test/java/movielibrary/core/)<br>

The `MovieTest` class is designed to verify the functionality of the Movie class within the `movielibrary.core` package. Utilizing the JUnit 5 framework, it includes several test methods that assess various aspects of the `Movie` class, such as the constructor, setter methods for the title, description, and movie length, as well as the functionality of the lending feature. JaCoCo reports the test coverage of instructions as 100%.


<b>JSON</b>:<br>
<b>Filepath</b>: [/movielibrary/core/src/test/java/movielibrary/json/](/movielibrary/core/src/test/java/movielibrary/json/) <br>

The `MovieDeserializerTest`, `MovieManagerTest`, and `MovieSerializerTest` classes collectively ensure the robustness of the movie management functionality within the application. Together, these test classes play a critical role in maintaining the integrity and reliability of the movie management system by ensuring that essential functionalities are thoroughly tested and validated. JaCoCo reports the test coverage of instruction as 96%.


## FXUI module 
The FXUI Module contains the main java class that executes the application (`App.java`), all associated FXML files that define the user interface, their corresponding controller classes, UI test files, and test versions of each FXML window. Similar to the Core Module, it also includes a `module-info` file and a `pom.xml` file.  

### FXML files
<b>Filepath</b>: [/movielibrary/fxui/src/main/resources/movielibrary/ui/](/movielibrary/fxui/src/main/resources/movielibrary/ui/)<br>

The FXML files define the structure and layout of the Movie Library application's user interface, controlling the visual elements such as buttons, text fields, and navigation between pages. 


### Controllers
<b>Filepath</b>: [/movielibrary/fxui/src/main/java/movielibrary/ui/](/movielibrary/fxui/src/main/java/movielibrary/ui/)<br>

The controllers handle the interaction between the user and the UI, responding to input actions and updating the interface based on the data or logic within the Movie Library app. `App.java` is also located in this folder. 


### Test files
<b>Filepath</b>: [/movielibrary/fxui/src/test/java/movielibrary/ui/](/movielibrary/fxui/src/test/java/movielibrary/ui/)<br>

The test file ensure that the UI elements and controllers function correctly, verifying that interaction and data updates occur as expected within the Movie Library application. The JaCoCo reports a 96% test coverage of instructions. 

<!-- Spring Boot module here  -->


## Links to documentation 
- [Release 1 documentation](/docs/release1/release1.md) <br>
- [Release 2 documentation](/docs/release2/release2.md) <br>
- [User stories](/movielibrary/src/main/java/readme.md) <br>
- [AI tools](/docs/release1/ai-tools.md) <br>
- [classDiagram after Release 2](/docs/release2/umlDiagrams/classDiagram.puml) <br>
- [packageDiagram after Release 2](/docs/release2/umlDiagrams/packageDiagram.puml)


