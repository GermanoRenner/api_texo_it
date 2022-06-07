package com.texoit.api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.texoit.api.models.Movie;

public interface MoviesRepository extends CrudRepository<Movie, Long>{

	List<Movie> findAll();
	Movie findByTitle(String title);
}
