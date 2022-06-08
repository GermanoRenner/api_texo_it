package com.texoit.api.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.texoit.api.models.Movie;
import com.texoit.api.models.Producer;
import com.texoit.api.models.response.WinnersResponse;
import com.texoit.api.repositories.MoviesRepository;

@Service
public class ProducersService {
	
	private static final Logger LOGGER = LogManager.getLogger(ProducersService.class);
	private static final String REGEX_SPLIT_PRODUCERS = ",|and ";
	
	@Autowired
	private MoviesRepository moviesRepository;
	
	public WinnersResponse getWinnersResponse() {
		var response = new WinnersResponse();
		var winnerProducers = getWinnersProducers();
		
		var winnerProducersMoreThanOne = winnerProducers.stream().filter(winner -> winner.getInterval() != null).toList();
//		var winnerProducersOnlyOnce = winnerProducers.stream().filter(winner -> winner.getInterval() == null).toList();

		var min = winnerProducersMoreThanOne.stream().sorted(Comparator.comparingInt(Producer::getInterval))
		  .toList().subList(0, 3);
		
		var max = winnerProducersMoreThanOne.stream().sorted(Comparator.comparingInt(Producer::getInterval).reversed())
				  .toList().subList(0, 3);
		
		response.setMax(max);
		response.setMin(min);
		
		return response;
	}
	
	public List<Producer> getWinnersProducers() {
		var winnerMovies = moviesRepository.findAllByWinner(Boolean.TRUE);
		List<Producer> winnerProducers = new ArrayList<>();
		var producers = getAllProducersOfMovies(winnerMovies);
		
		for(Producer producer : producers) {
			var moviesOfProducer = winnerMovies.stream().filter(movie -> movie.getProducers().contains(producer.getProducer())).toList();
			var previousWin = getMinYearMovie(moviesOfProducer);
			var followingWin = getMaxYearMovie(moviesOfProducer);
			var interval = (followingWin != previousWin) ? (followingWin - previousWin) : null;
			
			producer.setPreviousWin(previousWin);
			producer.setFollowingWin(followingWin);
			producer.setInterval(interval);
			producer.setCount(moviesOfProducer.size());
			winnerProducers.add(producer);
		}

		return winnerProducers.stream().sorted(Comparator.comparing(Producer::getInterval,
										  Comparator.nullsLast(Comparator.naturalOrder())))
								  .toList();
	}
	
	private List<Producer> getAllProducersOfMovies(List<Movie> movies) {
		var producerList = new ArrayList<Producer>();
		movies.stream().forEach(movie -> {
			var movieProducers = Arrays.asList(movie.getProducers().split(REGEX_SPLIT_PRODUCERS));
			
			movieProducers.stream().forEach(producerStr -> {
				var duplicatedProducer = producerList.stream()
													 .filter(producer -> producer.getProducer().equals(producerStr.trim()))
													 .findFirst();
				if(duplicatedProducer.isEmpty() && !producerStr.isBlank()) {
					producerList.add(new Producer(producerStr.trim()));					
				}
					
			});
		});
		return producerList;
	}
	
	private Integer getMinYearMovie(List<Movie> producerMovies) {
		var movieOlder = producerMovies.stream()
				.min(Comparator.comparingInt(Movie::getYear))
				.get();
		
		return !ObjectUtils.isEmpty(movieOlder) ? movieOlder.getYear() : null;
	}
	
	private Integer getMaxYearMovie(List<Movie> producerMovies) {
		var movieOlder = producerMovies.stream()
				.max(Comparator.comparingInt(Movie::getYear))
				.get();
		
		return !ObjectUtils.isEmpty(movieOlder) ? movieOlder.getYear() : null;
	}
}
