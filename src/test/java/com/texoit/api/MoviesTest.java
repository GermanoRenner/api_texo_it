package com.texoit.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

import com.texoit.api.models.Movie;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class MoviesTest {
	
	@Autowired
    private TestRestTemplate testRestTemplate;
	
	private static final String URI = "/movies";
	
	Movie movieRequest;
	
	@BeforeEach
	void setup() {
		this.movieRequest = new Movie();
		movieRequest.setProducers("Producer Test");
		movieRequest.setTitle("Title Movie Test");
		movieRequest.setYear(2020);
		movieRequest.setWinner(Boolean.TRUE);
	}

	
	@Test
	@Order(1)	
	void getAllMoviesTest() {
		ResponseEntity<List> response = testRestTemplate.exchange(URI, HttpMethod.GET, null, List.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	
	@Test
	@Order(2)
	void getByTitleTest() {
		var title = "Saturn 3";
		ResponseEntity<Movie> response = testRestTemplate.exchange(URI+"/"+title, HttpMethod.GET, null, Movie.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@Order(3)
	void saveMovieSuccessTest() {
		HttpEntity<Movie> httpEntity = new HttpEntity<>(movieRequest);
		
		ResponseEntity<Movie> response = testRestTemplate.exchange(URI, HttpMethod.POST, httpEntity, Movie.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@Order(4)
	void saveMovieAlreadyExistErrorTest() {
		movieRequest.setTitle("Saturn 3");
		HttpEntity<Movie> httpEntity = new HttpEntity<>(movieRequest);
		
		ResponseEntity<Movie> response = testRestTemplate.exchange(URI, HttpMethod.POST, httpEntity, Movie.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(5)
	void saveMovieInvalidTest() {
		movieRequest.setTitle(null);
		movieRequest.setProducers(null);
		HttpEntity<Movie> httpEntity = new HttpEntity<>(movieRequest);
		
		ResponseEntity<Movie> response = testRestTemplate.exchange(URI, HttpMethod.POST, httpEntity, Movie.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(6)
	void updateMovieSuccessTest() {
		movieRequest.setTitle("Title Movie Updated");
		movieRequest.setId(1);
		HttpEntity<Movie> httpEntity = new HttpEntity<>(movieRequest);
		
		ResponseEntity<Movie> response = testRestTemplate.exchange(URI, HttpMethod.PUT, httpEntity, Movie.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@Order(7)
	void updateMovieInvalidFieldsTest() {
		HttpEntity<Movie> httpEntity = new HttpEntity<>(movieRequest);
		
		ResponseEntity<Movie> response = testRestTemplate.exchange(URI, HttpMethod.PUT, httpEntity, Movie.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(8)
	void updateMovieAlreadyExistErrorTest() {
		movieRequest.setId(1);
		movieRequest.setTitle("Saturn 3");
		HttpEntity<Movie> httpEntity = new HttpEntity<>(movieRequest);
		
		ResponseEntity<Movie> response = testRestTemplate.exchange(URI, HttpMethod.PUT, httpEntity, Movie.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(8)
	void updateMovieNotFoundErrorTest() {
		movieRequest.setTitle("Title Movie Updated 2");
		movieRequest.setId(0);
		HttpEntity<Movie> httpEntity = new HttpEntity<>(movieRequest);
		
		ResponseEntity<Movie> response = testRestTemplate.exchange(URI, HttpMethod.PUT, httpEntity, Movie.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(8)
	void deleteMovieTest() {
		var deleteId = 1;
		
		ResponseEntity<Integer> response = testRestTemplate.exchange(URI+"/"+deleteId, HttpMethod.DELETE, null, Integer.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@Order(9)
	void deleteMovieNotFoundTest() {
		var deleteId = 0;
		
		ResponseEntity<Integer> response = testRestTemplate.exchange(URI+"/"+deleteId, HttpMethod.DELETE, null, Integer.class);
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	@Order(10) 
	void loadNewDatasetTest() {
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("dataset", new ClassPathResource("dataset.csv"));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
		
		ResponseEntity<HttpStatus> response = testRestTemplate.exchange(URI+"/loadNewDataset", HttpMethod.POST, requestEntity, HttpStatus.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

}
