# Movie Library 
[open in Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2024/gr2403/gr2403)

## Overview 
The Movie Library application is designed to assist librarians in maintaining an organized system for managing movie lending. It serves as both a self-checkout and self-check-in platform for the liberian to handle who wish to borrow or return movies. The app tracks movie lending by referencing a `movies.json` file, which indicates the availability of each movie.

The Frontpage is designed to provide an overview of all available movies for lending, presented in a down scrollable list. Users can select a movie they’re interested in and click the "More info" button, which takes them to the `MoviePage`. There is also a "Add movie" button. that takes the user to the `AddMoviePage`. 

The movie page offers detailed information about the selected film, including a brief summary and the movie duration in minutes. At the bottom of the page, there are four buttons: "Lend", "Return", "Delete" and "Cancel." By clicking "Lend", the movie will be lent to the user. "Return" button will take return the movie back to the library. Clicking "Cancel" will take the user back to the Front page. Lastly "Delete" button will delete the movie from the library.

On the Add movie page, the liberian can add a movie by entering the title, duration, and a brief movie description into the provided text fields. At the buttom of the page, there are two buttons: "Add Movie" and "Cancel". Clicking "Add Movie" will add the specified movie to the library, while the "Cancel" button will return the liberian back to the Front page. 

![Movielibrary application](/docs/images/MovieLibrary.png)
<b>Figure 1</b>: Three images showing Frontpage, Addmoviepage, and moviepage. 

## Developed by
- Adele Xiao Yuan Strysse 
- Helle Hornes Fystro
- Jennica Duong
- Sylvia Suet Wai Yung

## Building and Running the Project 
Before building and running the project, ensure that the following software is installed on your computer: Java 17, Maven, Sdkman, [WiX Toolset](https://github.com/wixtoolset/wix3/releases/tag/wix3141rtm) (for windows) and [dotnet](https://dotnet.microsoft.com/en-us/download) (for windows).

These tools are necessary for successful project setup and execution.

### Initializing the Project with Eclipse Che or Visual Studio Code
This project uses Maven for building and running. Follow these steps to start the application:

1. Open a terminal and navigate to the correct directory using `cd movielibrary`. From within gr2403/movielibrary, execute `mvn clean install -DskipTests` to build the project.
2. Navigate to the Spring Boot server directory with `cd springboot/restserver` and start the server by running `mvn spring-boot:run`.

The following steps apply if you're working in Eclipse Che: 

3. Open a new terminal while the Spring Boot server is running on the other terminal. 
4. Navigate to the movielibrary/fxui directory with `cd movielibrary/fxui`. 
5. Copy the `6080-tcp-desktop-ui` endpoint and open it in a new browser tab. 
6. In the same new terminal, run `mvn clean install` to complete the setup. 

You can now run the appliation in Eclipse Che, and view the application in the other browser tab. 

### Running the Application
This information is possible to do in both Eclipse Che and Vscode. Remember to have a terminal with the server running before running the appliation.

1. Navigate yourself to Movielibrary directory with `cd movielibrary`. 
2. After that `mvn clean install -DskipTests`, this is to compile, package, and install the maven project, making it possible to use locally. 
3. Then navigate to the fxui directory with `cd fxui`. 
4. Lastly, run the application with `mvn javafx:run`. 

The application is now launched, and the user can try the different functionalities. 

### Running the REST-API
To start the REST API server, follow the initial steps in [Initializing the Project with Eclips Che or Visual Studio Code](#initializing-the-project-with-eclipse-che-or-visual-studio-code). 

1. Navigate to movielibrary/springboot/restserver with `cd movielibrary/springboot/restserver`
2. Start the Spring Boot with `mvn spring-boot:run`. 

To stop the server, press `ctrl + C` in your terminal with the server running. 

### Using Jlink and Jpackage
To download and package the application locally, you can use Jlink and Jpackage in Visual Studio Code by following these steps: 

1. Ensure that the Spring Boot server is running, and open a new terminal. 
2. Navigate to the fxui directory: `cd fxui`. 
3. If not already completed, run `mvn clean install -DskipTests` to build the project. 
4. Execute `mvn javafx:jlink` to create a custom runtime image.  
5. Run `mvn jpackage:jpackage` to package the application.

The application should now be available in fxui/target/dist directory. Right-click on the folder, select "Reveal in File Explorer", and launch the `MovieLibraryfx` application to access the downloaded application on your computer. 

### Running Tests
To specifically run tests, use the following command from gr2403/movielibrary: `mvn clean test`. It is also possible to test without running the server if needed.

## Code quality
We utilized JaCoCo, SpotBugs and Checkstyle to ensure code quality, as metioned in [Release3.md](/docs/release2/release2.md). 

Before verifying code quality, please run the project with `mvn clean` and then `mvn install`from `gr2403/movielibrary`. Then you can proceed to quality check the project. 

To check the JaCoCo test coverage for the Java project, open the `index.html` file. Its location depends on the module of interest. For the Core module, the file can be found at [/movielibrary/core/target/site/jacoco/](/movielibrary/core/target/site/jacoco/), and for the FXUI module, the file is located at [/movielibrary/fxui/target/site/jacoco/](/movielibrary/fxui/target/site/jacoco/). Lastly the the Spring Boot module, the file is located at [/movielibrary/springboot/target/site/jacoco/](/movielibrary/springboot/target/site/jacoco/)

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
* <b>Jackson Core 2.17.2</b>: Used for processing JSON data, providing functionality to parse and generate JSON in the application. 
* <b>Jackson Databind 2.17.2</b>: Used to convert between Java objects and JSON, simplifying data serialization and deserialization. 
* <b>Hamcrest 2.2</b>: Used for writing expressive and readable assertions in tests, enhancing the clarity of test conditions. 
* <b>Spring Boot Devtools 3.3.5</b>: Used to speed up development with features like live reloading, automatic restarts, and other tools to enhance the development experience. 
* <b>Spring Boot Starter 3.3.5 </b>: Used to simplify application setup by providing core dependencies and basic configurations for a Spring Boot application. 

## Plugins
* <b>Maven Compiler 3.11.0</b>: Used to compile Java source code into bytecode, the Maven Compiler Plugin manages the compilation process.
* <b>Maven CheckStyle 3.5.0</b>: Used to ensure code style compliance by analyzing Java code for adherence to coding conventions. 
* <b>Maven Surfire 3.1.2</b>: Used to run tests in the Maven build lifecycle, facilitating automated test execution.
* <b>Maven Jar 3.0.2</b>: Used to package compiled Java code and resources into a JAR file, enabling easy distribution and execution of the application.
* <b>SpotBugs 4.8.6.4</b>: Used for static code analysis to identify potential bugs in the codebase. 
* <b>JaCoCo 0.8.12</b>: Used for measuring code coverage during testing, providing insights into which parts of the code are exercised by tests. 
* <b>WireMock 2.27.2</b>: Used to simulate HTTP services for testing, enabling the application to interact with mock server responses. 
* <b>Spring Boot Maven 2.7.5</b>: Used to package and deploy Spring Boot applications, simplifying the build process with dedicated goals for running, building, and deploying Spring Boot projects.

## Directory Structure 
The docs folder contains documentation for releases, as well as images folder for the different screenshot of the app. Movielibrary folder contains the project code, including core module, fxui module and Spring Boot module. The core, fxui and Spring Boot module, each contains classes, and tests for the corresponding classes. The fxui also contains fxml files. While in core folder it contains the JSON file and filehandling. The Spring Boot module contains the implementation of REST APIs, that allow for remote access and interaction with the movie library. Below is an illustration of the directory structure.

* [docs](/docs/)
    * [images](/docs/images/)
    * [release1](/docs/release1/)
    * [release2](/docs/release2/)
      * [umlDiagrams](/docs/release2/umlDiagrams/)
    * [release3](/docs/release3/)
      * [umlDiagrams](/docs/release3/umlDiagrams/)
* [movielibrary](/movielibrary/)
    * [core](/movielibrary/core/)
      * [src](/movielibrary/core/src/)
          * [main](/movielibrary/core/src/main/)
            * [java](/movielibrary/core/src/main/java/)
              * [movielibrary](/movielibrary/core/src/main/java/movielibrary/)
                * [core](/movielibrary/core/src/main/java/movielibrary/core/)
                * [json\internal](/movielibrary/core/src/main/java/movielibrary/json/internal/)
            * [resources\movielibrary\json\internal](/movielibrary/core/src/main/resources/movielibrary/json/internal/)
          * [test](/movielibrary/core/src/test/)
      * [target](/movielibrary/core/target/)
    * [fxui](/movielibrary/fxui/)
      * [src](/movielibrary/fxui/src/)
        * [main](/movielibrary/fxui/src/main/)
          * [java](/movielibrary/fxui/src/main/java/)
            * [movielibrary\ui](/movielibrary/fxui/src/main/java/movielibrary/ui/)
          * [resources\movielibrary\ui](/movielibrary/fxui/src/main/resources/movielibrary/ui/)
        * [test](/movielibrary/fxui/src/test/)
      * [target](/movielibrary/fxui/target/)
    * [springboot](/movielibrary/springboot/)
      * [restserver](/movielibrary/springboot/restserver/)
        * [src](/movielibrary/springboot/restserver/src/)
          * [main\java](/movielibrary/springboot/restserver/src/main/java/)
            * [movielibrary\springboot\restserver](/movielibrary/springboot/restserver/src/main/java/movielibrary/springboot/restserver/)
          * [test\java\movielibrary\springboot\restserver](/movielibrary/springboot/restserver/src/test/java/movielibrary/springboot/restserver/)
        * [target](/movielibrary/springboot/restserver/target/)
      * [target](/movielibrary/springboot/target/)

## Core Module 
The Core Module contains the java classes reponsible for managing the fundamental logic of the application, along with their respective test classes. Additionally, it includes a `module-info` file and a `pom.xml`. 

### Packages 
The core module is divided into two packages, core and json. 

<b>Core</b>: <br>
<b>Filepath</b>: [/movielibrary/core/src/main/java/movielibrary/core/](/movielibrary/core/src/main/java/movielibrary/core/) <br>

Core consist of `Movie.java`. `Movie` class represents a movie with attributes such as title, length, description, and lending status. It includes methods for data validation, and retrieval of movie properties, ensuring that the input values meet specified criteria. The class encapsulates movie-related functionalities, making it a crucal component of the Movie Library application. 

<b>JSON</b>: <br>
<b>Filepath</b>: [/movielibrary/core/src/main/java/movielibrary/json/](/movielibrary/core/src/main/java/movielibrary/json/internal/) <br>

JSON consists of `MovieDeserializer.java`, `MovieManager.java` and `MovieSerializer.java`. These three classes work together to manage a movie library's data efficiently. The `MovieDeserializer` class is responsible for deserializing JSON data from a file into a list of `Movie` objects, enabling the searching of movies by title, retrieving the complete list of movies, and checking their lending status using the Jackson library for JSON deserialization. The `MovieManager` class interacts with the `MovieSerializer` to facilitate the lending and returning of movies, ensuring that their lending status is accurately updated in the JSON file. Meanwhile, the `MovieSerializer` class handles the serialization of `Movie` objects back into the JSON format, allowing updates to the lending status and writing the entire movie list back to the file, thus ensuring the integrity and accuracy of the movie data.

### Test Files

<b>Core</b>:<br>
<b>Filepath</b>: [/movielibrary/core/src/test/java/movielibrary/core/](/movielibrary/core/src/test/java/movielibrary/core/)<br>

The `MovieTest` class is designed to verify the functionality of the Movie class within the `movielibrary.core` package. Utilizing the JUnit 5 framework, it includes several test methods that assess various aspects of the `Movie` class, such as the constructor, setter methods for the title, description, and movie length, as well as the functionality of the lending feature. JaCoCo reports the test coverage to be 100%.

<b>JSON</b>:<br>
<b>Filepath</b>: [/movielibrary/core/src/test/java/movielibrary/json/](/movielibrary/core/src/test/java/movielibrary/json/) <br>

The `MovieDeserializerTest`, `MovieManagerTest`, and `MovieSerializerTest` classes collectively ensure the robustness of the movie management functionality within the application. Together, these test classes play a critical role in maintaining the integrity and reliability of the movie management system by ensuring that essential functionalities are thoroughly tested and validated. JaCoCo reports the test coverage 92%.


## FXUI Module 
The FXUI Module contains the main java class that executes the application (`App.java`), all associated FXML files that define the user interface, their corresponding controller classes, UI test files, and test versions of each FXML window. Similar to the Core Module, it also includes a `module-info` file and a `pom.xml` file.  

### FXML Files
<b>Filepath</b>: [/movielibrary/fxui/src/main/resources/movielibrary/ui/](/movielibrary/fxui/src/main/resources/movielibrary/ui/)<br>

The FXML files define the structure and layout of the Movie Library application's user interface, controlling the visual elements such as buttons, text fields, and navigation between pages. 


### Controllers
<b>Filepath</b>: [/movielibrary/fxui/src/main/java/movielibrary/ui/](/movielibrary/fxui/src/main/java/movielibrary/ui/)<br>

The controllers handle the interaction between the user and the UI, responding to input actions and updating the interface based on the data or logic within the Movie Library app. `App.java` is also located in this folder. 


### Test Files
<b>Filepath</b>: [/movielibrary/fxui/src/test/java/movielibrary/ui/](/movielibrary/fxui/src/test/java/movielibrary/ui/)<br>

The test file ensure that the UI elements and controllers function correctly, verifying that interaction and data updates occur as expected within the Movie Library application. The JaCoCo reports a test coverage of 88%. 

## Spring Boot Module

### Restserver Files
<b>Filepath</b>: [/movielibrary/springboot/restserver/src/main/java/movielibrary/springboot/restserver](/movielibrary/springboot/restserver/src/main/java/movielibrary/springboot/restserver/) <br>

The Spring Boot Module contains java classes that execute the Spring Boot server with `MovieLibraryApplication.java`, `MovieLibraryService.java` and `MovieLibraryController.java`. It also contains different exceptions and advice classes, as well as test files. Similar to both Fxui and Core Module, it also includes a `module-info` file and a `pom.xml` file.

### Test Files
<b>Filepath</b>: [/movielibrary/springboot/restserver/src/test/java/movielibrary/springboot/restserver/](/movielibrary/springboot/restserver/src/test/java/movielibrary/springboot/restserver/) <br>

The test files in the Spring Boot module verify the functionality and reliability of various components, including controllers, services, and exception handling within the movielibrary application’s REST server. Using JaCoCo, these tests achieve 83% code coverage.

## Links to documentation 
- [Release 1 documentation](/docs/release1/release1.md) <br>
- [Release 2 documentation](/docs/release2/release2.md) <br>
- [Release 3 documentation](/docs/release3/release3.md) <br>
- [User stories](/movielibrary/core/src/main/java/readme.md) <br>
- [AI tools](/docs/ai-tools.md) <br>
- [classDiagram](/docs/release3/umlDiagrams/classDiagram.puml) <br>
- [packageDiagram](/docs/release3/umlDiagrams/packageDiagram.puml) <br>
- [sequenceDiagram](/docs/release3/umlDiagrams/sequenceDiagram.puml) <br>
- [REST-API](/docs/rest-api.md) <br>
- [Challenges](/docs/release3/challenges.md) <br>
- [Contribution](/docs/release3/contribution.md) <br>
- [Sustainability](/docs/release3/sustainability.md) <br>




