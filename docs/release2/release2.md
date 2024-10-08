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

## PlantUML


## Code quality
<!-- Ps: legg med brukerdataeksempelet også i denne mappen hvor dere har en fil typ «userData.json» --> 

We used JaCoCo, SpotBugs and Checkstyle for code quality check, as metioned above in [Milestones reached](#milestones-reached). 

The JaCoCo output file shows a 98% coverage of instructions and 93% coverage of branches, in core module. Core is divided into a file handling classes and a movie class. The file handling classes have 96% coverage of instructions, and 83% coverage of branches. `Movie` class has 100% coverage both in instructions and branches. 

The FXUI module has 96% coverage of instructions and 91% coverage of branches. The `FrontPageController` has 95% coverage of instructions, and 83% coverage of branches. While `MoviePageController` has 100% coverage of both instructions and branches. The `App` has 79% coverage of instructions. The reason why `App`coverage is not > 80%, is because it require us to test the main method. But we will test the start method that loads the page, this is to avoid the JavaFX thread issue when running the main method. This is because the main method doesn't handle any direct initialization logic.

As metioned in [Milestones reached](#milestones-reached), Checkstyle were used for proper formating, while SpotBugs were used to avoid bad code practices. 

## Experiences
Following release 1, we adopted pair programming to facilitate knowledge sharing and perform peer reviews to ensure code quality. Initally, we followed conventional commit practices, maintaining consistency with commit types, but used different emojis. After receiving feedback from our Teaching Assistent (TA), we standardized the use of emojis by assigning one specific emoji to each commit type.  

Since Release 1, we have consistently utilized GitLab features such as Milestones, Labels, assigned code reviewers, and releases. This has enhanced collaboration within the group and maximized the benefits of GitLab's tools. 

## Relevant links 
