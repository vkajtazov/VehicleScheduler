package models;

public class Municipality {
	private String municipalityName;
	private City municipalityCity;
	private Double municipalityLatitude;
	private Double municipalityLongitude;
	public String getMunicipalityName() {
		return municipalityName;
	}
	public void setMunicipalityName(String municipalityName) {
		this.municipalityName = municipalityName;
	}
	public City getMunicipalityCity() {
		return municipalityCity;
	}
	public void setMunicipalityCity(City municipalityCity) {
		this.municipalityCity = municipalityCity;
	}
	public Double getMunicipalityLatitude() {
		return municipalityLatitude;
	}
	public void setMunicipalityLatitude(Double municipalityLatitude) {
		this.municipalityLatitude = municipalityLatitude;
	}
	public Double getMunicipalityLongitude() {
		return municipalityLongitude;
	}
	public void setMunicipalityLongitude(Double municipalityLongitude) {
		this.municipalityLongitude = municipalityLongitude;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((municipalityLatitude == null) ? 0 : municipalityLatitude
						.hashCode());
		result = prime
				* result
				+ ((municipalityLongitude == null) ? 0 : municipalityLongitude
						.hashCode());
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
		Municipality other = (Municipality) obj;
		if (municipalityLatitude == null) {
			if (other.municipalityLatitude != null)
				return false;
		} else if (!municipalityLatitude.equals(other.municipalityLatitude))
			return false;
		if (municipalityLongitude == null) {
			if (other.municipalityLongitude != null)
				return false;
		} else if (!municipalityLongitude.equals(other.municipalityLongitude))
			return false;
		return true;
	}
	
	
}
