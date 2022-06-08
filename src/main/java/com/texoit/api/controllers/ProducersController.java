package com.texoit.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.api.models.Producer;
import com.texoit.api.models.response.WinnersResponse;
import com.texoit.api.services.ProducersService;

@RestController
@RequestMapping("/producers")
public class ProducersController extends BaseController {
	
	@Autowired
	private ProducersService producersService;

	@GetMapping("/winners")
	public ResponseEntity<List<Producer>> getWinners() {
		var producers = producersService.getWinnersProducers();		
		return ResponseEntity.ok().body(producers);
	}
	
	@GetMapping("/winnersResponse")
	public ResponseEntity<WinnersResponse> getWinnersResponse() {
		var producers = producersService.getWinnersResponse();	
		return ResponseEntity.ok().body(producers);
	}
}
