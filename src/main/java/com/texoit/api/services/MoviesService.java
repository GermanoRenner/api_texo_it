package com.texoit.api.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.texoit.api.exceptions.BadRequestException;
import com.texoit.api.models.Movie;
import com.texoit.api.repositories.MoviesRepository;

@Service
public class MoviesService {
	

	private static final Logger LOGGER = LogManager.getLogger(MoviesService.class);
	private static final String PLEASE_CHECK_THE_MOVIE_FIELDS = "Please, check the movie fields.";
	
	@Autowired
	private MoviesRepository moviesRepository;
	
	public List<Movie> getAllMovies(){
		return moviesRepository.findAll();
	}
	
	public Movie getByTitle(String title){
		return moviesRepository.findByTitle(title);
	}
	
	public Movie saveMovie(Movie movieRequest) {
		if(!checkIfMovieIsValid(movieRequest)) {
			LOGGER.error(PLEASE_CHECK_THE_MOVIE_FIELDS);
			throw new BadRequestException(PLEASE_CHECK_THE_MOVIE_FIELDS);
		}
		
		var existingMovie = getByTitle(movieRequest.getTitle());
		if(!ObjectUtils.isEmpty(existingMovie)) {
			LOGGER.error("Already exist a movie with same title.");
			throw new BadRequestException("Already exist a movie with same title.");
		}
		var movie = ObjectUtils.isEmpty(existingMovie) ? new Movie() : existingMovie;
		
		movie.setTitle(movieRequest.getTitle());
		movie.setProducers(movieRequest.getProducers());
		movie.setYear(movieRequest.getYear());
		movie.setStudios(movieRequest.getStudios());
		movie.setWinner(movieRequest.getWinner());
		
		return moviesRepository.save(movie);
	}
	
	public Movie updateMovie(Movie movieRequest) {
		if(!checkIfMovieIsValid(movieRequest) || ObjectUtils.isEmpty(movieRequest.getId())) {
			LOGGER.error(PLEASE_CHECK_THE_MOVIE_FIELDS);
			throw new BadRequestException(PLEASE_CHECK_THE_MOVIE_FIELDS);
		}
		
		var existingMovie = moviesRepository.findById(movieRequest.getId());
		if(ObjectUtils.isEmpty(existingMovie)) {
			LOGGER.error("The specified movie was not found.");
			throw new BadRequestException("The specified movie was not found.");
		}
		
		existingMovie.setTitle(movieRequest.getTitle());
		existingMovie.setProducers(movieRequest.getProducers());
		existingMovie.setYear(movieRequest.getYear());
		existingMovie.setStudios(movieRequest.getStudios());
		existingMovie.setWinner(movieRequest.getWinner());
		
		return moviesRepository.save(existingMovie);
	}
	
	@Transactional
	public Integer deleteMovie(Integer id) {
		return moviesRepository.deleteById(id);
	}
	
	private Boolean checkIfMovieIsValid(Movie movieRequest) {
		if(movieRequest.getTitle().isBlank() || movieRequest.getProducers().isBlank() || movieRequest.getYear() == null) {
			return false;
		}		
		
		return true;		
	}
	
}
