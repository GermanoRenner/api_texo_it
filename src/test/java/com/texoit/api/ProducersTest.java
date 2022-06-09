package com.texoit.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.texoit.api.models.Producer;
import com.texoit.api.models.response.WinnersResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class ProducersTest {

	@Autowired
    private TestRestTemplate testRestTemplate;
	
	private static final String URI = "/producers";
	
	private Boolean validWinnersResponse(WinnersResponse response) {
		var winnersMinMax = response.getMin();
		winnersMinMax.addAll(response.getMax());
		for(Producer win : winnersMinMax){
			if((win.getFollowingWin()-win.getPreviousWin()) != win.getInterval()) {
				return false;
			}
		}
		
		return true;
	}
	
	@Test
	@Order(1)	
	void getWinnersTest() {
		ResponseEntity<List> response = testRestTemplate.exchange(URI+"/winners", HttpMethod.GET, null, List.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@Order(2)	
	void getWinnersResponseTest() {
		ResponseEntity<WinnersResponse> response = testRestTemplate.exchange(URI+"/winnersResponse", HttpMethod.GET, null, WinnersResponse.class);
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(validWinnersResponse(response.getBody()), Boolean.TRUE);
	}
	
	@Test
	@Order(3)	
	void getWinnersResponseIncorrectIntervalTest() {
		ResponseEntity<WinnersResponse> response = testRestTemplate.exchange(URI+"/winnersResponse", HttpMethod.GET, null, WinnersResponse.class);
		response.getBody().getMin().get(0).setInterval(0);
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(validWinnersResponse(response.getBody()), Boolean.FALSE);
	}
}
