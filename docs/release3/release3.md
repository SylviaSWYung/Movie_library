# Release 3 Documentation 

## Milestones Reached
In this third release, our primarly focus has been on adding additonal functionality, expanding our JavaFX interface, and developing and implementing a REST-API. 

The new functionalities include an "add movie" method and an option to delete movies from the library. These methods have been integrated in to `MovieManager.java`. A new FXML file has been created to support the `addMovie()` method, along with a corresponding controller file, `AddMoviePageController.java`. Additionally, a delete button has been added to the MoviePage FXML, with a delete method implemented in `MoviePageController.java`. The `deleteMovieFromLibrary()` is implemented in `MovieSerializer.java`. By clicking on a movie, the user is directed to the MoviePage, where they can delete the selected movie from the library by pressing the delete button. New tests have been created to validate these funcitons, and existing tests have been updated to reflect the changes mentioned above. We have still utilized Checkstyle, SpotBugs and Jacoco, more detail can be found in [code-quality](#code-quality).

The additional functionality that has been implemented are an add movie method, and the possibility to delete movies from the library. This additional methods has been added in `MovieManager.java`. A new FXML file has been created to hold the new addmethod(), and corresponding `AddMoviePageController.java` has also been made. A delete method has been made in the MoviePage fxml, and delete method has been implemented in `FrontPageController.java`. By clicking on movie, the user would be taken to the MoviePage, and the by clicking on the button delete, the chosen movie would be deleted from the library. New testers were developed to test the new functions, and we also updated testers to cover the changes metion above. 

We have also enhanced the application to utilize a REST API through a Spring Boot `restserver` module. Within the `restserver` directory, three main classes have been implemented: `MovieLibraryService.java`, `MovieLibraryApplication.java` and `MovieLibraryController.java`. Additionally, various `advice` and `exception` classes have been added to handle specific exceptions, ensuring robust error management throughout the application. These three main classes mentioned above, are primarily responsible for handling HTTP requests between the server and the application. Further details can be found in [rest-api.md](/docs/rest-api.md). New tests have also been developed for the `restserver` module to ensure its functionality.

We have further encapsulated the core functionality within an API, so the UI no longer interacts with the `core` module directly. When user interacts with the UI, such as by pressing buttons to execute different functions, `RemoteMovieLibraryAccess.java` within the fxui module sends an HTTP request and HTTP response to the REST API, which then delegates the task to the `core` module. As a result, the application now required the server to be running in order to function. 

Other major changes in Release 3 include improvements in file handling, creation of a deployable product, and implementation of GitLab CI. We modified the file handling process following Release 1 and 2, so files are now generated in the `user.home` directory. The change ensures easier local usage of the application and compatibility with the JAR file. Addiontally, we have explored various GitLab tools, such as implementing GitLab CI/CD Pipeline, and using commit squashing. More details can be found in the [work routines](#work-routines) section. Finally, the project has been configured as a shippable product using JPackage and JLink, allowing users to download and run the application locally. 

## User Stories  
Our final user stories are detailed in [readme.md](/movielibrary/core/src/main/java/readme.md) in the core-main folder for Java. Most aspects of the user stories initially outlined in Release 1 have been addressed in Release 2 and Release 3, with the following updates: 

* **Additional Methods in MovieManager.java and MovieSerializer.java**: In the previous releases, we planned to expand functionality. In this release, we implemented the `addMovie()` method and `deleteMovie()` method in `MovieManager.java`, as well as `addMovieToLibrary()` method and `deleteMovieFromLibrary()` method in `MovieSerializer.java`. 

However, one feature from Release 1, **Button Functionality**, was not implemented as originally intended. Initially, the plan was to disable the button if a movie had already been lent. After further development, we chose an alternative solution: when the "lend" button is clicked twice, an error message will display. 

## Work Routines
Our work routine consisted of daily scrums and mandatory meetings every Wednesday and Thursday from 14:00 to 16:00 at Helgasetr. We also utilized GitLab's tools to establish effective workflows and routines, as outlined below: 

- **Milestones**: Milestones were used from Release 1 through Release 3, giving us an overview of available, ongoing, and completed issues, as well as tasks to be completed before each deadline. 

- **Releases**: After each submission, we used GitLab's Releases tool to mark the project's current state with a specific tag on the commit. 

- **Issues**: Throughout the project, we created issues for each task or required change. The issue titles were general yet informative, and each issue was assigned a primary coder, labels, and a milestone. Descriptions varied in detail depending on the complexity of the task. 

- **Branches**: A new branch was created for each issue by selecting "Create branch" next to "Create merge request", with the branch names corresponding to issue names. 

- **Conventional Commits**: We ensured consistency in our commit messages by using the VSCode extension "Conventional Commits" from the start of the project. Each commit type was paired with a specific gitmoji. If the appropriate gitmoji was unclear, only the commit type was used. 

- **Squashed commits**: Starting two to three weeks into Release 3, we began squashing commits to improve efficiency, ensuring each commit in the master branch contained fully functional code. 

- **Labels**: We consistently used labels throughout the project to prioritize each issue and differentiate between types such as Bugs, Critical, Documentation, Enhancement, and Test. 

- **Merge Requests**: When an issue was complete, the developer submitted a merge request on GitLab. The request included labels, closed the branch, identified the assigned developer, and co-authores, and passes through a CI/CD pipeline check before merging. A code reviewer from the opposite pair programming group reviewed the code before it was approved and merged by the assignee. 

- **Code Review**: For each merge request, a code reviewer from the opposite pair programming group reviewed the change and left comments. If approved, the reviewer would leave an approval; if issues needed clarification, the reviewer opened a discussion or comment thread on the merge request. 

- **CI/CD Pipeline**: In Release 3, we implemented a pipeline to ensure the code build successfully, passed checkstyle, spotbugs, and tests, and was properly cleaned. The pipeline helped us merge complete, functional code and reduce errors in future branches. 

- **Meetings**: We held mandatory meetings every Wednesday and Thursday, starting with a daily scrum followed by a project discussion. This allowed all group members to contribute ideas for project direction. Additionally, a "Group Contract" specified that developers who did not fulfill their responsibilites would contribute 50kr to a team fund for future team-building activies. 

- **Pair Development**: Starting in Release 2, we found pair programming effective, and continued to utilize it in Release 3. Developers were reassigned partners after each release. Pairing sometimes involved working on the same branch, or programming together on one developer's computer. 

## Code Quality
We used JaCoCo, SpotBugs and Checkstyle for code quality check. As metioned in [Release 2](/docs/release2/release2.md), Checkstyle were used for proper formating, while SpotBugs were used to avoid bad code practices. 

The JaCoCo output file shows a test coverage of 94% in core module. The FXUI module has 88% test coverage. Lastly the springboot.restserver module shows a test coverage of 83%. 

## Diagrams
Figure 1, shows a diagram that outlines the package architecture of the Movie Library project, highlightning the relationship between its main components. It consists of four primary components: core, fxui, javafx and RESTserver. Core Module contains two packages: `movielibrary.core` for core functionalities, and `movielibrary.json` for handling JSON data. FXUI Module represents the user interface package, `movielibrary.ui`, which interacts with the RESTserver Module conatining: `REST-API`, which provides endpoints to manage a collection of movies, and uses SpringBoot. The relationship illustrate how the user interface (fxui) depends on JavaFX. The RESTserver interacts with both the core functionalities and JSON handling packages. The core package also relies on the JSON package for data handling, which, in turn, depends on the Jackson library for JSON processing.This modular design enhances the maintainability and scalability of the application. <br>

![packageDiagram.puml](https://www.planttext.com/api/plantuml/png/VPB1Zi8W54Nt9Fm3wRvyWb4pBcOtcVe3p_PPiIsG1wqrndyVgCvORPsmUuTxkHTOEW_aswOM5l8AIlnGK18qqeOPPvovIcaBOI9MHEQK-NEDeW1tm2BYt3JMQDHUv8PGN3WJlqwYCPt2Mkq8w2mZVWkFpcZEhi-6-xvL_nYsQg8uGWVx_gOP-uHsm-mweyYhS7R4B-HxasqTKeVqd26985rzVIOJIMP9wN9jZF_pYsle8QLStFDnDkb-mpFtzMRdFJPdevG3U4HuqOO8AQdouDF5gDhKOEPPE5kYBi9t-040"packageDiagram.puml")<br>
<b>Figure 1</b>: Package Diagram for Project Movie Library. <br>

Figure 2, illustrates a class diagram with its key classes and their relationships with the Movie Library project. This class diagram for the Movie Library project shows the relationships between key classes: `Movie`, `MovieSerializer`, `MovieDeserializer`, and `MovieManager`. The `Movie` class represents individual movies, with attributes like title, length, description, and lent status, alongside methods for managing them. `MovieSerializer` and `MovieDeserializer` handle the serialization and deserialization of movie data using `ObjectMapper` and a file system. `MovieManager` oversees movie operations, such as lending and returning movies, relying on `MovieSerializer` for data management. The diagram emphasizes the interaction between these classes to efficiently manage and track movie information. <br>

![classDiagram.puml](https://www.planttext.com/api/plantuml/png/dLLDRzim3BtxLmWvEKCseFTXw8OiG806BP02Eoisut2L9KEYMtJ3_liOoRByrM7R9R39nyV79ygvTIGjjRKIXPBERL5MLjOVcciaotnC7CmpWlYP2N4aYxeIXAH0y7jfsXC_rLV07UYABYAXId25nORGQ0wUZ54WjK37GE90O2qiI0B_d0_PDmlScmLciKuDAw37Qss-Jj8uTaWC-HYzJJm-4pLoQkzb-kYpmLAuo3qKESmEkyob3v73hlE1XaWlOMnSdxv8NIe8zL-jDJPoeMxQASnB_uPqEJQo03V2ZoS9-AqV8C3YCAXR0ZS59k7Tx4iJ_HfjqX4iIeK_mFgTkZzzXu8Eicau42p3avNsbNCxvCvd3ClcYxbXAgzdaV4XNWqxagJMpOQDA4-nTplJwZATzej5WizA-PRkmGBHQyhAilI9HzCfpSCkUwuULOA2RhDtrjGHkkXwmMvMy8RSJIzMmql8JoqTUV1FfjwXeu-UurF0kRrEq33N6NKvlw7hINMyQObWTAUA2nHF-xDVwKKFK2E54O9dB33lfVOe2yh8e6OhIUPJ9mvIowepeQ-EanmdgvFU8vg7y8tNiIJo3RvJNMX1tD-DuBeUyum2Ni95StW3Mwk_oEA9y_DqtCZ_-eGkRUn41NiyScDrinBl_U_4rso-ZXtsNOVDfYUn4bV07n4tGeY0YHs77RBibdtZFwpV)
<b>Figure 2 </b>: Class diagram for Project Movie Library.

Figure 3, shows a sequence diagram that visiualizes the case where a user adds a new movie to the library in the application. The diagram shows what happens from the user interaction with the MovieLibrary application to the POST http request call from the rest API.
![sequenceDiagram.puml](https://www.planttext.com/api/plantuml/png/hLPDRnen4BtxLqpf1QGKqggNSgWIfOcQ2a8KagfNinx0hTVUshCXwQ_lsBi4I3Q0agp4VdWyRsRUphpbA93qLEO60lujqMRuJSkPbtctu4KXP4REmqr0BqGymuUlKEhkcNUMBkKCJ_ZgdJ7e-v0PdVs1zrAfeRlLE24hchT6aBDVmy70IHKT3L_RhLuUlOoUUEdlUwNk7TT0qRaxlSjDgoDMRdKcwvlMaz5MavP6_qDetYAs93ScNkg3qg4myXwU908XGuiRZ3oVHr4IfD908Kbj0aYhdY6h6ML7Y5ikKqd2EjQKILH1I4Fmix9eP-7KU-S5yB4rJAneLJX69v1i8rXecYz3HS0SGs1tWIPW5GZEO_pTCT9o7omkgjisQJ9uK94qG3kZ-G5p5JAlYnYginkIZLUOEy9a7rGzT9nbd6CVbXotEMAwgG2NgCyvHjpHv3TcD9H54MbPU4t8fPPu7CRajPqzWjo1zfseE34QBVLvtBW9Bc-kmSUv2mJaO7C2qaEeUFyn7bt0nAbxaUB4TpikQrpuTFWHJZno1wXaZSSgr4vwF8R2MNw9JJWxAjhIbPcpK-rp25MUwuZxjb_jMZTWwu8RfXea7U9BHVMkjH_ibZGzKkdjjNjGWdPdFymz1Em1SwbUI-0dAjQefsiIrc3VByj7IhQPKIrZ4IHDyHchK-cHM-hpuHTWe2cB5Bsgfng1NjvKDFTk0UVyC9E6QoCylSimgSHB6cnTtrh90v8MyHBFWU8DTpfGgDiiSJIYEIFLJBsA6fUG-FjBPU1fKVXc_5oLbdJ-XjJ4Sb55YUAngsQmfiGg4N_YYC_yX-4_)
<b>Figure 3 </b>: Sequence diagram for Project Movie Library.

## Experiences
Following [Release 2](/docs/release2/release2.md), we continued using pair programming due to its effectiveness. After receiving feedback, we consulted our Teaching Assistant (TA) for areas where our group could improve. His feedback led us to begin approving merge requests, as we were initially unaware of this practice in Release 1 and 2. We also found out that the code reviewer only needed to approve, not merge, the request. As a result, two weeks before the final submission deadline, we adopted a workflow where the assignee waits for the reviewer's approval before merging the request. 

Since Release 2, we have consistenly utilized GitLab features such as Milestones, Labels, code reviewers, Releases, and the CI/CD pipeline. We also moved project discussion from Messenger to the merge request threads as needed. 

On our final workday, 14.11. 2024, the CI/CD pipeline experienced significant delays due to high traffic on NTNU's servers, as confirmed by our TA. To ensure we submitted a fully completed project without waiting for the pipeline to build, we thoroughly reviewed our code for errors in both VSCode and Eclipse Che before force-pushing the code to master after the merge request. 

We would also like to clarify an important aspect to our sensors. We consulted Professor Stoica, the course instructor, to clarify requirements regarding the shippable product and server use. Currently, the application only operates if the server runs on local machine. This means that if User A performs actions and saves data on their computer, another user, User B, on a different computer would not see these changes. While synchronization across users could be achieved using Eclipse Che's localhost, we sought clarification on whether this was necessary for this release and course. Professor Stoica confirmed that supporting shared data across users was not a requirement, so we did not prioritize this functionality. But it is a possible function to implement if desired. As of now, this product is designed to run on a single computer without concurrent multi-user access. 

## Relevant links 
[classDiagram](/docs/release3/umlDiagrams/classDiagram.puml) <br>
[packageDiagram](/docs/release3/umlDiagrams/packageDiagram.puml) <br>
[sequenceDiagram](/docs/release3/umlDiagrams/sequenceDiagram.puml) <br>
[challenges](/docs/release3/challenges.md) <br>
[contribution](/docs/release3/contribution.md) <br>
[sustainability](/docs/release3/sustainability.md) <br>
[Rest-api](/docs/rest-api.md) <br>
[user-story](/movielibrary/core/src/main/java/readme.md) <br>
[Readme at root level](/readme.md)
