package models;

import java.sql.Time;

public class CityBus implements VehicleI{
	private String lineNumber;
	private String startingMunicipality;
	private String arrivingMunicipality;
	private Time startTime;
	private int regularity;
	private int Type;
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getStartingMunicipality() {
		return startingMunicipality;
	}
	public void setStartingMunicipality(String startingMunicipality) {
		this.startingMunicipality = startingMunicipality;
	}
	public String getArrivingMunicipality() {
		return arrivingMunicipality;
	}
	public void setArrivingMunicipality(String arrivingMunicipality) {
		this.arrivingMunicipality = arrivingMunicipality;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public int getRegularity() {
		return regularity;
	}
	public void setRegularity(int regularity) {
		this.regularity = regularity;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
}
