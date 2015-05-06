package models;

import java.sql.Time;

public class Line {

	private Station startingStation;

	private Station arrivingStation;

	private Time traveledTime;

	private String kmTraveled;

	public Station getStartingStation() {
		return startingStation;
	}

	public void setStartingStation(Station startingStation) {
		this.startingStation = startingStation;
	}

	public Station getArrivingStation() {
		return arrivingStation;
	}

	public void setArrivingStation(Station arrivingStation) {
		this.arrivingStation = arrivingStation;
	}

	public Time getTraveledTime() {
		return traveledTime;
	}

	public void setTraveledTime(Time traveledTime) {
		this.traveledTime = traveledTime;
	}

	public String getKmTraveled() {
		return kmTraveled;
	}

	public void setKmTraveled(String kmTraveled) {
		this.kmTraveled = kmTraveled;
	}

	@Override
	public String toString() {
		return "Line [startingStation=" + startingStation
				+ ", arrivingStation=" + arrivingStation + ", traveledTime="
				+ traveledTime + ", kmTraveled=" + kmTraveled + "]";
	}

}
