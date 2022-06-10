package com.texoit.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.api.models.Producer;
import com.texoit.api.models.response.WinnersResponse;
import com.texoit.api.services.ProducersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/producers")
@Api(value = "Producers")
public class ProducersController extends BaseController {
	
	@Autowired
	private ProducersService producersService;

	@ApiOperation(value = "Get all winner producers.", produces = "application/json")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, response = List.class, message = "Load a new Dataset.")
	})
	@GetMapping("/winners")
	public ResponseEntity<List<Producer>> getWinners() {
		var producers = producersService.getWinnersProducers();		
		return ResponseEntity.ok().body(producers);
	}
	
	@ApiOperation(value = "Get all winning producers ranked by time of year.", produces = "application/json")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, response = WinnersResponse.class, message = "Get all winning producers ranked by time of year.")
	})
	@GetMapping("/winnersResponse")
	public ResponseEntity<WinnersResponse> getWinnersResponse(@RequestParam(required = false) Integer limit) {
		var producers = producersService.getWinnersResponse(limit);	
		return ResponseEntity.ok().body(producers);
	}
}
