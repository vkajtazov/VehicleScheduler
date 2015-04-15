package pdfReaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.CityBus;

public class PrivateBusScheduler {

	private static String path = "bus schedule.txt";
	
	public static List<CityBus> parseAllBusses () throws IOException{
		
		ArrayList<CityBus> cityBusList = new ArrayList<CityBus>();
		ArrayList<String> rows = TrainScheduler.readFromDirectoriesNames(path);
		
		for (String line : rows) {
			cityBusList.addAll(parseBusSchedule(line));
		}
		
		return cityBusList;
	}

	private static List<CityBus> parseBusSchedule(String line) {

		PrivateBusParser scheduler = new PrivateBusParser(line);
		return scheduler.getAll();
	}

	public static CityBus.REGULARITY getRegularity(String regularityString) {
		if (regularityString.equals("EveryDay"))
			return CityBus.REGULARITY.EveryDay;
		else if (regularityString.equals("Saturday")) {
			return CityBus.REGULARITY.Saturday;
		} else if (regularityString.equals("Sunday")) {
			return CityBus.REGULARITY.Sunday;
		}
		return null;
	}
}
