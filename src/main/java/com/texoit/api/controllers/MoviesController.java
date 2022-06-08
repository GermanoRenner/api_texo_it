package com.texoit.api.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.texoit.api.models.Movie;
import com.texoit.api.services.MoviesService;

@RestController
@RequestMapping("/movies")
public class MoviesController extends BaseController{
	
	@Autowired
	private MoviesService moviesService;
	
	@GetMapping
	public ResponseEntity<List<Movie>> getAllMovies() {
		var movies = moviesService.getAllMovies();		
		return ResponseEntity.ok().body(movies);
	}
	
	@GetMapping("/{title}")
	public ResponseEntity<Movie> getByTitle(@PathVariable("title") String title) {
		var movie = moviesService.getByTitle(title);		
		return ResponseEntity.ok().body(movie);
	}
	
	@PostMapping
	public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
		var movieSaved = moviesService.saveMovie(movie);		
		return ResponseEntity.ok().body(movieSaved);
	}
	
	@PutMapping
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
		var movieSaved = moviesService.updateMovie(movie);	
		return ResponseEntity.ok().body(movieSaved);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deleteMovie(@PathVariable("id") Integer id) {
		var deleted = moviesService.deleteMovie(id);		
		if (deleted == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
	}

	
}
