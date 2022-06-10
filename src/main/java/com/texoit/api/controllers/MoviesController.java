package com.texoit.api.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.texoit.api.models.Movie;
import com.texoit.api.services.MoviesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/movies")
@Api(value = "Movies")
public class MoviesController extends BaseController{
	
	@Autowired
	private MoviesService moviesService;
	
	@ApiOperation(value = "Get All movies in the database.", produces = "application/json")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, response = List.class, message = "Get All movies in the database.")
	})
	@GetMapping
	public ResponseEntity<List<Movie>> getAllMovies() {
		var movies = moviesService.getAllMovies();		
		return ResponseEntity.ok().body(movies);
	}
	
	@ApiOperation(value = "Get a specific movie by Title.", produces = "application/json")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, response = Movie.class, message = "Get a specific movie by Title.")
	})
	@GetMapping("/{title}")
	public ResponseEntity<Movie> getByTitle(@PathVariable("title") String title) {
		var movie = moviesService.getByTitle(title);		
		return ResponseEntity.ok().body(movie);
	}
	
	@ApiOperation(value = "Save a new movie in the database.", produces = "application/json")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, response = Movie.class, message = "Save a new movie to the database."),
	    @ApiResponse(code = 400, message = "Please, check the movie fields.; Already exist a movie with same title."),
	})
	@PostMapping
	public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
		var movieSaved = moviesService.saveMovie(movie);		
		return ResponseEntity.ok().body(movieSaved);
	}
	
	@ApiOperation(value = "Update a movie in the database.", produces = "application/json")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, response = Movie.class, message = "Update a movie in the database."),
	    @ApiResponse(code = 400, message = "Please, check the movie fields.; The specified movie was not found.; Already exist a movie with same title."),
	})
	@PutMapping
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
		var movieSaved = moviesService.updateMovie(movie);	
		return ResponseEntity.ok().body(movieSaved);
	}
	
	@ApiOperation(value = "Delete a movie in the database.", produces = "application/json")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, response = Integer.class, message = "Delete a movie in the database.")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deleteMovie(@PathVariable("id") Integer id) {
		var deleted = moviesService.deleteMovie(id);		
		if (deleted == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Load a new Dataset.", produces = "application/json")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, response = HttpStatus.class, message = "Load a new Dataset."),
	    @ApiResponse(code = 400, message = "Invalid File Format.Allow only CSV files.; The CSV header is incorret.; The maximum rows number is X."),

	})
	@PostMapping("/loadNewDataset")
	public ResponseEntity<HttpStatus> loadNewDataset(@RequestParam("dataset") MultipartFile file) throws IOException {
		moviesService.loadNewDataset(file);
		return ResponseEntity.ok().body(HttpStatus.OK);
	}

	
}
