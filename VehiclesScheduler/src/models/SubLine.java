package models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SubLine {

	private Station startingStation;

	private Station arrivingStation;

	private Time traveledTime;

	private String kmTraveled;

	private String linePassing;

	public String getLinePassing() {
		return linePassing;
	}

	public void setLinePassing(String linePassing) {
		this.linePassing = linePassing;
	}

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
		return "SubLine [startingStation=" + startingStation
				+ ", arrivingStation=" + arrivingStation + ", traveledTime="
				+ traveledTime + ", kmTraveled=" + kmTraveled
				+ ", linePassing=" + linePassing + "]";
	}

}
