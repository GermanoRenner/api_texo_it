package com.texoit.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.texoit.api.models.Movie;
import com.texoit.api.repositories.MoviesRepository;

@Service
public class MoviesService {

	@Autowired
	private MoviesRepository moviesRepository;
	
	public List<Movie> getAllMovies(){
		return moviesRepository.findAll();
	}
	
	public Movie getByTitle(String title){
		return moviesRepository.findByTitle(title);
	}
}
