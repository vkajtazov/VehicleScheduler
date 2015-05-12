package models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Line {

	private String transporter;
	private String routeShortName;
	private Time oneWayStartTime;
	private Time oneWayArriveTime;
	private Time backWayStartTime;
	private Time backWayArriveTime;
	private String distance;
	private Station startStation;
	private Station arrivingStation;
	private String regularity;
	private List<SubLine> sublineList;

	public Line() {
		sublineList = new ArrayList<SubLine>();
	}

	public String getRegularity() {
		return regularity;
	}

	public void setRegularity(String regularity) {
		this.regularity = regularity;
	}

	public Station getStartStation() {
		return startStation;
	}

	public void setStartStation(Station startStation) {
		this.startStation = startStation;
	}

	public Station getArrivingStation() {
		return arrivingStation;
	}

	public void setArrivingStation(Station arrivingStation) {
		this.arrivingStation = arrivingStation;
	}

	public void addSubLine(SubLine subline) {
		sublineList.add(subline);
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getRouteShortName() {
		return routeShortName;
	}

	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	public List<SubLine> getSublineList() {
		return sublineList;
	}

	public void setSublineList(List<SubLine> sublineList) {
		this.sublineList = sublineList;
	}

	public Time getOneWayStartTime() {
		return oneWayStartTime;
	}

	public void setOneWayStartTime(Time oneWayStartTime) {
		this.oneWayStartTime = oneWayStartTime;
	}

	public Time getOneWayArriveTime() {
		return oneWayArriveTime;
	}

	public void setOneWayArriveTime(Time oneWayArriveTime) {
		this.oneWayArriveTime = oneWayArriveTime;
	}

	public Time getBackWayStartTime() {
		return backWayStartTime;
	}

	public void setBackWayStartTime(Time backWayStartTime) {
		this.backWayStartTime = backWayStartTime;
	}

	public Time getBackWayArriveTime() {
		return backWayArriveTime;
	}

	public void setBackWayArriveTime(Time backWayArriveTime) {
		this.backWayArriveTime = backWayArriveTime;
	}

	@Override
	public String toString() {
		return "Line [transporter=" + transporter + ", routeShortName="
				+ routeShortName + ", oneWayStartTime=" + oneWayStartTime
				+ ", oneWayArriveTime=" + oneWayArriveTime
				+ ", backWayStartTime=" + backWayStartTime
				+ ", backWayArriveTime=" + backWayArriveTime + ", distance="
				+ distance + ", startStation=" + startStation
				+ ", arrivingStation=" + arrivingStation + ", regularity="
				+ regularity + ", sublineList=" + sublineList + "]";
	}

}
