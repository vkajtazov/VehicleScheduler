package models;

import java.sql.Time;

public class Train implements VehicleI {
	private String startingCity;
	private String arrivingCity;
	private Time startTime;
	private Time arrivingTime;

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
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((arrivingCity == null) ? 0 : arrivingCity.hashCode());
		result = prime * result
				+ ((arrivingTime == null) ? 0 : arrivingTime.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result
				+ ((startingCity == null) ? 0 : startingCity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Train other = (Train) obj;
		if (arrivingCity == null) {
			if (other.arrivingCity != null) {
				return false;
			}
		} else if (!arrivingCity.equals(other.arrivingCity)) {
			return false;
		}
		if (arrivingTime == null) {
			if (other.arrivingTime != null) {
				return false;
			}
		} else if (!arrivingTime.equals(other.arrivingTime)) {
			return false;
		}
		if (startTime == null) {
			if (other.startTime != null) {
				return false;
			}
		} else if (!startTime.equals(other.startTime)) {
			return false;
		}
		if (startingCity == null) {
			if (other.startingCity != null) {
				return false;
			}
		} else if (!startingCity.equals(other.startingCity)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("Starting city: " + startingCity + "\n");
		builder.append("Arriving city: " + arrivingCity + "\n");
		builder.append("Start time: " + startTime.toString()+"\n");
		builder.append("Arriving time: " + arrivingTime.toString()+"\n");
		
		return builder.toString();
	}

}
