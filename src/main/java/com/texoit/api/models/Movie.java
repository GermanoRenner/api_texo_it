package com.texoit.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="movies")
public class Movie {
	
	@Column(name="ano")
	private Integer year;
	@Id
	private String title;
	private String studios;
	private String producers;
	private Boolean winner;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStudios() {
		return studios;
	}
	public void setStudios(String studios) {
		this.studios = studios;
	}
	
	public String getProducers() {
		return producers;
	}
	public void setProducers(String producers) {
		this.producers = producers;
	}
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer yearMovie) {
		this.year = yearMovie;
	}
	
	public Boolean getWinner() {
		return winner;
	}
	public void setWinner(Boolean winner) {
		this.winner = winner;
	}
	

}
