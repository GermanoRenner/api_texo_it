package com.texoit.api.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.texoit.api.exceptions.BadRequestException;
import com.texoit.api.exceptions.ServiceException;
import com.texoit.api.helper.CsvHelper;
import com.texoit.api.models.Movie;
import com.texoit.api.repositories.MoviesRepository;
import com.texoit.api.services.MoviesService;

@Service
public class MoviesServiceImpl implements MoviesService {
	

	private static final String ALREADY_EXIST_MOVIE_TITLE_ERROR = "Already exist a movie with same title.";
	private static final Logger LOGGER = LogManager.getLogger(MoviesServiceImpl.class);
	private static final String PLEASE_CHECK_THE_MOVIE_FIELDS = "Please, check the movie fields.";
	private static final String[] HEADER_CSV_MOVIES = {"year", "title", "studios", "producers", "winner"};
	
	@Autowired
	private MoviesRepository moviesRepository;
	
	@Override
	public List<Movie> getAllMovies(){
		return moviesRepository.findAll();
	}
	
	@Override
	public Movie getByTitle(String title){
		return moviesRepository.findByTitle(title);
	}
	
	@Override
	public Movie saveMovie(Movie movieRequest) {
		if(!checkIfMovieIsValid(movieRequest)) {
			LOGGER.error(PLEASE_CHECK_THE_MOVIE_FIELDS);
			throw new BadRequestException(PLEASE_CHECK_THE_MOVIE_FIELDS);
		}
		
		if(checkIfExistMovieSameTitle(movieRequest.getTitle())) {
			LOGGER.error(ALREADY_EXIST_MOVIE_TITLE_ERROR);
			throw new BadRequestException(ALREADY_EXIST_MOVIE_TITLE_ERROR);
		}
		var movie = new Movie();		
		movie.setTitle(movieRequest.getTitle());
		movie.setProducers(movieRequest.getProducers());
		movie.setYear(movieRequest.getYear());
		movie.setStudios(movieRequest.getStudios());
		movie.setWinner(movieRequest.getWinner());
		
		return moviesRepository.save(movie);
	}
	
	@Override
	public Movie updateMovie(Movie movieRequest) {
		if(!checkIfMovieIsValid(movieRequest) || ObjectUtils.isEmpty(movieRequest.getId())) {
			LOGGER.error(PLEASE_CHECK_THE_MOVIE_FIELDS);
			throw new BadRequestException(PLEASE_CHECK_THE_MOVIE_FIELDS);
		}

		if(checkIfExistMovieSameTitle(movieRequest.getTitle())) {
			LOGGER.error(ALREADY_EXIST_MOVIE_TITLE_ERROR);
			throw new BadRequestException(ALREADY_EXIST_MOVIE_TITLE_ERROR);
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
	
	@Override
	@Transactional
	public Integer deleteMovie(Integer id) {
		return moviesRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public void loadNewDataset(MultipartFile file) throws IOException {
		var list = CsvHelper.readCSV(file, HEADER_CSV_MOVIES);
		var newMovieList = buildMovieDatasetList(list);
		updateMovieDataset(newMovieList);
	}
	
	private Boolean checkIfMovieIsValid(Movie movieRequest) {
		if(ObjectUtils.isEmpty(movieRequest.getTitle()) || ObjectUtils.isEmpty(movieRequest.getProducers()) || ObjectUtils.isEmpty(movieRequest.getYear())) {
			return false;			
		}
		
		return true;	
	}
	
	private Boolean checkIfExistMovieSameTitle(String title) {
		return !ObjectUtils.isEmpty(getByTitle(title));
	}
	
	private List<Movie> buildMovieDatasetList(List<List<String>> list) {
		try {
			List<Movie> movieList = new ArrayList<>();		
			for (List<String> row : list) {
				var movie = new Movie();
				movie.setYear(Integer.valueOf(row.get(0)));
				movie.setTitle(row.get(1));
				movie.setStudios(row.get(2));
				movie.setProducers(row.get(3));
				if(row.size() == HEADER_CSV_MOVIES.length) {
					movie.setWinner(defineWinner(row.get(4)));
				}
				
				movieList.add(movie);
			}
			
			return movieList;
		} catch(Exception ex) {
			throw new ServiceException(ex.getMessage());
		}
	}
	
	private Boolean defineWinner(String winner) {
		if(!ObjectUtils.isEmpty(winner) && winner.toLowerCase().equals("yes")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private void updateMovieDataset(List<Movie> newList) {
		moviesRepository.deleteAll();
		moviesRepository.saveAll(newList);
	}
	
	
}
