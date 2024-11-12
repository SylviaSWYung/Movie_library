# MovieLibrary REST-API

The REST-API is built using SpringBoot and provides endpoints to manage a collection of movies. To interact with the REST-API one would need to send HTTP requests to the server. The requests will each start with `baseURL/movielibrary/movies`. The baseURL will be `http://localhost:`.

## API Overview
The API allows clients to:
- Retrieve movie information
- Add new movies or delete existing movies
- Manage the rental status for each movie

Data exchanges are in JSON format, using the Jackson library for serializing and deserializing.

## Managing Movie
To manage movies we use the following endpoints:
- <strong>Retrieve all movies from the library:</strong> Send a <strong>GET request</strong> to `baseURL/movielibrary/movies` to get a list of all the movies in the library and their information
- <strong>Retrieve a specific movie by its movieTitle:</strong> Send a <strong>GET request</strong> to `baseURL/movielibrary/movies/{title}` to get the movie with the given title and its information
- <strong>To get the rent staus of a movie:</strong> Send a <strong>GET request</strong> to `baseURL/movielibrary/movies/{title}/lentstatus` to get the lent status of the movie, this will either be `false` or `true`

## Lend Movie 
To lend a movie we use the following endpoints:
- <strong>Lending a movie:</strong> Send a <strong>POST request</strong> to `baseURL/movielibrary/movies/{title}/lend` to select the movie with the given title and changing its rentStatus to `true`, indicating that it has been lent

## Return Movie 
To return a movie we use the following endpoints:
- <strong>Returning a movie:</strong> Send a <strong>POST request</strong> to `baseURL/movielibrary/movies/{title}/return` to select the movie with the given title and changing it's rentStatus back to `false`, indicating that it is no longer lent

## Add and Delete movies 
To add a new movie or delete an existing movie from the library we use the following endpoints:
- <strong>Add a new movie:</strong> Send a <strong>PUT request</strong> to `baseURL/movielibrary/movies` with the movie information given in JSON format within the request body
- <strong>Delete a movie:</strong> Send a <strong>DELETE request</strong> to `baseURL/movielibrary/movies/{title}` to delete the movie with the given title from the library

## HTTP requests and usage
| `HTTP verb` | `Endpoint` | Description |
|----------|----------|----------|
| `GET` | `/movielibrary/movies` | Retrieve a list of all movies
| `GET` | `/movielibrary/movies/{title}` | Retrieve a movie with a given title
| `GET` | `/movielibrary/movies/{title}/lentstatus` | Retrive the lent status of a movie with a given title
| `POST` | `/movielibrary/movies/{title}/lend` | Lends a movie by changing its status to `true`
| `POST` | 	`/movielibrary/movies/{title}/return` | Returns a movie by changing its status to `false`
| `PUT` | `/movielibrary/movies` | Adding a new movie into the library
| `DELETE` | `/movielibrary/movies/{title}` | Deleting an existing movie from the library

## Sequence diagram
An example of how the GET request is being implemented is shown in the sequence diagram in [release 3](docs/release3/release3.md)

## How to run the springboot
To run the SpringBoot type the following commands in the terminal:
```shell
cd movielibrary/springboot/restserver

mvn spring-boot:run

# Option + C to stop the server
```
