package com.texoit.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.api.models.Movie;
import com.texoit.api.services.MoviesService;

@RestController
@RequestMapping("/movies")
public class MoviesController {
	
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

	
}
