package models;

import java.util.ArrayList;
import java.util.List;



public class Taxi implements VehicleI {

	private String taxiName;
	private String taxiLocation;
	private String taxiAddress;
	private List<String> taxiPhones;
	private String taxiInfo;

	public Taxi() {
		taxiPhones = new ArrayList<String>();
	}

	public String getTaxiName() {
		return taxiName;
	}

	public String getTaxiInfo() {
		return taxiInfo;
	}

	public void setTaxiInfo(String taxiInfo) {
		this.taxiInfo = taxiInfo;
	}

	public void setTaxiName(String taxiName) {
		this.taxiName = taxiName;
	}

	public String getTaxiLocation() {
		return taxiLocation;
	}

	public void setTaxiLocation(String taxiLocation) {
		this.taxiLocation = taxiLocation;
	}

	public String getTaxiAddress() {
		return taxiAddress;
	}

	public void setTaxiAddress(String taxiAddress) {
		this.taxiAddress = taxiAddress;
	}

	public void addTaxiPhone(String phone) {
		taxiPhones.add(phone);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("Taxi name: " + taxiName + "\n");
		builder.append("Taxi Address: " + taxiAddress + "\n");
		builder.append("Taxi location: " + taxiLocation + "\n");
		builder.append("Taxi phones: " + taxiPhones.toString() + "\n");
		
		return builder.toString();
	}
}
