# Release 2 documentation 

## Milestones Reached
In this second release, our primary focus has been on improving code quality and refining the project's architecture. We have modularized the application by separating the fxui and core layers into distinct modules, each with its own `pom.xml` file to manage dependencies and configurations. <br>

This release also focused on extending and refining the various `pom.xml` files, including the addition of Jackson dependencies for handling the JSON file format. Additionally, we configured Checkstyle, SpotBugs, and JaCoCo to ensure higher code quality and coverage. <br>

We have also converted from saving in a CSV file to a JSON format using the Jackson library. Jackson was used to help us serialize Java objects into JSON and deserialize JSON strings into Java objects. Several methods in `MovieManager.java` were refactored and moved into dedicated classes - `MovieDeserializer.java` and `MovieSerializer.java`. The FXML controllers were updated to reflect these changes, ensuring consistency across the codebase. <br>

We have also developed new tests for both the core logic and the UI. Core tests were updated to cover the changed mentioned above, including the creating specific tests such as `MovieDeserializerTest.java` and `MovieSerializer.java`. UI tests were designed to cover the user stories, using TestFX4, similar to the individual project structure from earlier in the semester. 

JaCoCo has also been implemented to monitor test coverage. The result from JaCoCo helped us identify areas for improvement, allowing us to enhance test coverage and ensure that more functionality is tested comprehensively. <br>

Furthermore, Checkstyle and SpotBugs were introduced to maintain high code quality. Checkstyle enforces consistent coding standards by verifying that the code follows a defined style rule, enhancing readability and maintainability. SpotBugs were used to detect potential bugs in the application, and ensures that we catch issues early in the development process. 

## User stories 
Our user stories were initially outlined in [release1.md](/docs/release1/release1.md), and are described in detail in [readme.md](/movielibrary/core/src/main/java/readme.md) located in the core-main folder for Java. In [release1.md](/docs/release1/release1.md), we highlighted several aspects that we intended to address in future updates. For this release, the following updates have been made: 

* **Movie Selection**: Previously, we aimed to offer a wider variety of movies to enhance the user experience. In this release, two additional movies have been added to the selection. 
* **Correct Update to Movies.json file**: In the previous release, the `movies.csv` file was only updated in the target folder. In this release, the `movies.json` file located in `movielibrary/core/src/main/resources/movielibrary` is now correctly updated instead of the one in the target folder.

However, there are still some features from Release 1 that have not yet been implemented. These features are postponed because the primary focus of Release 2 was on improving code quality. More details can be found in the [Experiences](#experiences) section.  

## Code quality
We used JaCoCo, SpotBugs and Checkstyle for code quality check, as metioned above in [Milestones reached](#milestones-reached). 

The JaCoCo output file shows a 100% coverage of both instructions and branches in core module. Core is divided into a file handling classes and a movie class. The FXUI module has 96% coverage of instructions and 91% coverage of branches. The `FrontPageController` has 95% coverage of instructions, and 83% coverage of branches. While `MoviePageController` has 100% coverage of both instructions and branches. The `App` has 79% coverage of instructions. The reason the `App` does not exceed 80% coverage is due to the need to test the `main` method. However, we will focus on testing the `start` method, which loads the application page, to avoid issues with the JavaFX thread when executing the `main` method. The `main` method itself does not handle any direct initialization logic.

As metioned in [Milestones reached](#milestones-reached), Checkstyle were used for proper formating, while SpotBugs were used to avoid bad code practices. 

## Persistence to file with JSON
The data in the `movies.json` file are represented as objects. Currently, the file contains four `Movie` objects, each with the following keys: title, movieLength, description, and isLent. The `title` key holds a string representing the movie's title, `movieLength` contains a double representing the movie's duration, `description` holds a string with the movie's description, and `isLent` is a boolean indicating whether the movie is lent (`true`) or available (`false`). When the `lend` method in `MovieManager.java` is executed, the `isLent` key is updated to `true`, and when the `returnBack` method is executed, the `isLent` key is changed back to `false`.

## PlantUML
Figur 1, shows a diagram that outlines the package architecture of the Movie Library project, highlightning the relationship between its main components. It consists of three primary components: core, fxui, and javafx. Core Module contains two packages: `movielibrary.core` for core functionalities, and `movielibrary.json` for handling JSON data. FXUI Module represents the user interface package, `movielibrary.ui`, which interacts with both the core functionalities and JSON handling packages. The relationship illustrate how the user interface (fxui) depends on JavaFX, while `movielibrary.ui` interacts with both the core and JSON packages. The core package also relies on the JSON package for data handling, which, in turn, depends on the Jackson library for JSON processing. This modular design enhances the maintainability and scalability of the application. <br>

![packageDiagram.puml](https://www.plantuml.com/plantuml/png/ZOzB3eCm34JtFaLEa2DKB7eHDmqe4CTHFeWASVSgeKWKXDhjpFECfua9GyfaXKSrOAVl1hk097qc2w2OF3ljab0Sj9X1R9mW7essvX4ml6Hnhxm-ieDbdzLEsLndPVDDzpXYErL-7q6sIiiHLaDa9-OYrnufvUsJ0OUUrJiF-OtLJQV3ly2YR4_LNm6WqUwPoRu1 "packageDiagram.puml")<br>
<b>Figur 1</b>: Package Diagram for Project Movie Library. <br>

Figur 2, illustrates a class diagram with its key classes and their relationships with the Movie Library project. This class diagram for the Movie Library project shows the relationships between key classes: `Movie`, `MovieSerializer`, `MovieDeserializer`, and `MovieManager`. The `Movie` class represents individual movies, with attributes like title, length, description, and lent status, alongside methods for managing them. `MovieSerializer` and `MovieDeserializer` handle the serialization and deserialization of movie data using `ObjectMapper` and a file system. `MovieManager` oversees movie operations, such as lending and returning movies, relying on `MovieSerializer` for data management. The diagram emphasizes the interaction between these classes to efficiently manage and track movie information. <br>

![classDiagram.puml](https://www.plantuml.com/plantuml/png/bPFTJiCm38NlynJMhWO29EuH0bIG9cq2gRn0rfhEB4shnDq4Y7T7JPicRGSID_knlnoV4xTtbgNXfbIGAMbjGh8mihogsueGhWQRwa08Nm8WPKEw02PM208WhvgjGYXRO8swu1q4AaUR6QgPAYq0jbMbK6eWkqRDWIgGNrkvvLdGAv0tGT9r0fq4MTVnmWNooccxwg6Y7ApNZdt7tNZAF4jCnbFd-MBIMHYd7TexgNE5tVb7OohZjKZNpHHBdA4Ey-P8Duns2WOZVPPJ3hz7wqpHa5JqYSOjzddxZXblP5sZwQ-0jaQQ3m7mH0hXZRgDkyC9sd2yd5N6Isal86N9ZJszXgCXndkbdB9zCSZy4Q-mbnqTlXW6Q3nsujE08qV_YhWcopTEuxRZx4e7DExwvpG0pdZtlZoHpjslMMplFTjXjb-zkGVcvDKOL9NiH1B9SZdDk95Q5dsy8O8ta4vM5Fvxka1klqVlYoiCMaD7eSv_yyYDqGyosozJZjhVGemyBguNSEa-9-x4V5qzUzwpOffd0Itm9t4D01tZ9yOJXBX3dJUb-W40 "classDiagram.puml")<br>
<b>Figur 2</b>: Class diagram for Project Movie Library.

## Experiences
Following [Release 1](/docs/release1/release1.md), we adopted pair programming to facilitate knowledge sharing and perform peer reviews to ensure code quality. Initally, we followed conventional commit practices, maintaining consistency with commit types, but used different emojis. After receiving feedback from our Teaching Assistent (TA), we standardized the use of emojis by assigning one specific emoji to each commit type. Since Release 1, we have consistently utilized GitLab features such as Milestones, Labels, assigned code reviewers, and releases. This has enhanced collaboration within the group and maximized the benefits of GitLab's tools. 

In Release 1, we intended to implement certain functionalities; however, due to the emphasis on code quality in Release 2 instead of quantity of methods, we postponed some of these functions to Release 3.

## Relevant links 
[classDiagram](/docs/release2/umlDiagrams/classDiagram.puml) <br>
[packageDiagram](/docs/release2/umlDiagrams/packageDiagram.puml) <br>
[Readme at root level](/readme.md)
