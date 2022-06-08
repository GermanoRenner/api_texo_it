package com.texoit.api.models.response;

import java.util.List;

import com.texoit.api.models.Producer;

public class WinnersResponse {

	private List<Producer> min;
	private List<Producer> max;
	
	public List<Producer> getMin() {
		return min;
	}
	public void setMin(List<Producer> min) {
		this.min = min;
	}
	public List<Producer> getMax() {
		return max;
	}
	public void setMax(List<Producer> max) {
		this.max = max;
	}
}
