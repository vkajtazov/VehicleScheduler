package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Line;
import models.Station;
import busLines.BusLinesGetter;
import busLines.BusStopsLocationGetter;

public class TestForBusStops {

	public static void main(String[] args) throws IOException, InterruptedException {
		List<Station> list = BusStopsLocationGetter.get();
		// BusStopInfoGetter.addStationName(list);
		// System.out.println(list);
		List<Line> resultList = new ArrayList<Line>();
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				List<Line> lineList = BusLinesGetter.parseRoutes(list.get(i)
						.getLocation(), list.get(j).getLocation());
				resultList.addAll(lineList);
				Thread.sleep(500);
			}
		}
		System.out.println(resultList);
	}

}
