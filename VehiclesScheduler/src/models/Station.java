package models;

import org.apache.xerces.impl.io.Latin1Reader;

public class Station {

	private String stationName;

	private double stationLatitude;

	private double stationLongitude;
	private String placeID;

	public Station(String stationName, double stationLatitude,
			double stationLongitude) {
		super();
		this.stationName = stationName;
		this.stationLatitude = stationLatitude;
		this.stationLongitude = stationLongitude;
	}

	public Station() {
	}

	public String getPlaceID() {
		return placeID;
	}

	public void setPlaceID(String placeID) {
		this.placeID = placeID;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getStationLatitude() {
		return stationLatitude;
	}

	public void setStationLatitude(double stationLatitude) {
		this.stationLatitude = stationLatitude;
	}

	public double getStationLongitude() {
		return stationLongitude;
	}

	public String getLocation() {
		return stationLatitude + "," + stationLongitude;
	}

	public void setStationLongitude(double stationLongitude) {
		this.stationLongitude = stationLongitude;
	}

	@Override
	public String toString() {
		return "Station [stationName=" + stationName + ", stationLatitude="
				+ stationLatitude + ", stationLongitude=" + stationLongitude
				+ "]";
	}

}
