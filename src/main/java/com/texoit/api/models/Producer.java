package com.texoit.api.models;

public class Producer {

	private String producer;
	private Integer interval;
	private Integer count;
	private Integer previousWin;
	private Integer followingWin;
	
	public Producer(String producer) {
		this.producer = producer;
	}
	
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getPreviousWin() {
		return previousWin;
	}
	public void setPreviousWin(Integer previousWin) {
		this.previousWin = previousWin;
	}
	public Integer getFollowingWin() {
		return followingWin;
	}
	public void setFollowingWin(Integer followingWin) {
		this.followingWin = followingWin;
	}
}
