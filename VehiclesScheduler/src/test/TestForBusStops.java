package test;

import java.io.IOException;
import java.util.List;

import models.Station;
import busLines.BusLinesGetter;
import busLines.BusStopInfoGetter;
import busLines.BusStopsLocationGetter;

public class TestForBusStops {

	public static void main(String[] args) throws IOException {
		List<Station> list = BusStopsLocationGetter.get();
		//BusStopInfoGetter.addStationName(list);
		//System.out.println(list);
		Station start = list.get(0);
		Station end = list.get(1);
		BusLinesGetter.firstMethod(start.getLocation(), end.getLocation());
	}

}
