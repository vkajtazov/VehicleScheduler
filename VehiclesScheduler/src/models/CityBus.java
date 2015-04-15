package models;

import java.sql.Time;

public class CityBus implements VehicleI {

	public static enum REGULARITY {
		EveryDay, Saturday, Sunday

	}

	public static enum TYPE {

		Public, Private
	}

	private String lineNumber;
	private Time startTime;
	private TYPE type;
	private String startingStation;
	private String arrivingStation;
	private REGULARITY regularity;

	public String getStartingStation() {
		return startingStation;
	}

	public void setStartingStation(String startingStation) {
		this.startingStation = startingStation;
	}

	public String getArrivingStation() {
		return arrivingStation;
	}

	public void setArrivingStation(String arrivingStation) {
		this.arrivingStation = arrivingStation;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public REGULARITY getRegularity() {
		return regularity;
	}

	public void setRegularity(REGULARITY regularity) {
		this.regularity = regularity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Line Number: " + lineNumber + "\n");
		builder.append("Type: " + type + "\n");
		builder.append("Start time: " + startTime + "\n");
		builder.append("Regularity: " + regularity + "\n");

		return builder.toString();
	}

}
