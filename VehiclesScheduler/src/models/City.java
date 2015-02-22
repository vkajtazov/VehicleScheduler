package models;

public class City {
	private String cityName;
	private Double cityLatitude;
	private Double cityLongitude;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Double getCityLatitude() {
		return cityLatitude;
	}
	public void setCityLatitude(Double cityLatitude) {
		this.cityLatitude = cityLatitude;
	}
	public Double getCityLongitude() {
		return cityLongitude;
	}
	public void setCityLongitude(Double cityLongitude) {
		this.cityLongitude = cityLongitude;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cityLatitude == null) ? 0 : cityLatitude.hashCode());
		result = prime * result
				+ ((cityLongitude == null) ? 0 : cityLongitude.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (cityLatitude == null) {
			if (other.cityLatitude != null)
				return false;
		} else if (!cityLatitude.equals(other.cityLatitude))
			return false;
		if (cityLongitude == null) {
			if (other.cityLongitude != null)
				return false;
		} else if (!cityLongitude.equals(other.cityLongitude))
			return false;
		return true;
	}
	
	

	
	

}
