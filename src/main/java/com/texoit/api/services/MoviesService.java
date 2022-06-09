package com.texoit.api.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.texoit.api.models.Movie;

public interface MoviesService {

	List<Movie> getAllMovies();

	Movie getByTitle(String title);

	Movie saveMovie(Movie movieRequest);

	Movie updateMovie(Movie movieRequest);

	Integer deleteMovie(Integer id);

	void loadNewDataset(MultipartFile file) throws IOException;

}