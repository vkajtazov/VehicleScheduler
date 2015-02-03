package models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class IntercityBus implements VehicleI {
	private String startingCity;
	private String arrivingCity;
	private Time startTime;
	private Time arrivingTime;
	private List<String> passingCities;
	private String regularity;
	private String trasporter;
	private int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStartingCity() {
		return startingCity;
	}

	public void setStartingCity(String startingCity) {
		this.startingCity = startingCity;
	}

	public String getArrivingCity() {
		return arrivingCity;
	}

	public void setArrivingCity(String arrivingCity) {
		this.arrivingCity = arrivingCity;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getArrivingTime() {
		return arrivingTime;
	}

	public void setArrivingTime(Time arrivingTime) {
		this.arrivingTime = arrivingTime;
	}

	public String getRegularity() {
		return regularity;
	}

	public void setRegularity(String regularity) {
		this.regularity = regularity;
	}

	public String getTrasporter() {
		return trasporter;
	}

	public void setTrasporter(String trasporter) {
		this.trasporter = trasporter;
	}

	public List<String> getPassingCities() {
		return passingCities;
	}

	public void setPassingCities(List<String> passingCities) {
		this.passingCities = passingCities;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Starting City: " + startingCity + "\n");
		builder.append("Arriving City: " + arrivingCity + "\n");
		builder.append("Starting time: " + startTime.toString() + "\n");
		if (arrivingTime != null) {
			builder.append("Arriving time: " + arrivingTime.toString() + "\n");
		}
		builder.append("Passing cities: " + passingCities.toString() + "\n");
		builder.append("Trasport company: " + trasporter + "\n");
		return builder.toString();
	}

}
