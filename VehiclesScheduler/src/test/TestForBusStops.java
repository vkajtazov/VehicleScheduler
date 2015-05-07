package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.SubLine;
import models.Station;
import busLines.BusLinesGetter;
import busLines.BusStopsLocationGetter;

public class TestForBusStops {

	public static void main(String[] args) throws IOException, InterruptedException {
		List<Station> list = BusStopsLocationGetter.get();
		// BusStopInfoGetter.addStationName(list);
		// System.out.println(list);
		List<SubLine> resultList = new ArrayList<SubLine>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				List<SubLine> lineList = BusLinesGetter.parseRoutes(list.get(i)
						.getLocation(), list.get(j).getLocation());
				resultList.addAll(lineList);
				Thread.sleep(800);
			}
		}
		System.out.println(resultList);
	}

}
