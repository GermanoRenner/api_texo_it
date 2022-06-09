package com.texoit.api.services;

import java.util.List;

import com.texoit.api.models.Producer;
import com.texoit.api.models.response.WinnersResponse;

public interface ProducersService {

	WinnersResponse getWinnersResponse(Integer limit);

	List<Producer> getWinnersProducers();

}