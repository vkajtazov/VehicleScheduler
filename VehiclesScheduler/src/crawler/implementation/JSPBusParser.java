package crawler.implementation;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import models.CityBus;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JSPBusParser {

	public static List<CityBus> parseCityBuses(List<HtmlPage> pages) {
		List<CityBus> cityBusList = new ArrayList<CityBus>();
		/*
		 * for (HtmlElement htmlElement : pages) { parseCityBus(htmlElement); }
		 */
		CityBus bus = new CityBus();
		String lineNumber = parseCityBusLineNumber(pages.get(0));
		cityBusList.addAll(parseCityBusSchedule(pages.get(0), lineNumber));
		return cityBusList;
	}

	private static String parseCityBusLineNumber(HtmlPage page) {
		HtmlElement elem = page.getFirstByXPath("//table[@width='250']");
		HtmlElement lineNumber = elem.getFirstByXPath("tbody//strong");
		return lineNumber.asText();
	}

	private static List<CityBus> parseCityBusSchedule(HtmlPage page, String lineNumber) {
		List<CityBus> cityBusList = new ArrayList<CityBus>();

		HtmlElement elem = page
				.getFirstByXPath("//table[@width='480'][@cellpadding='2']");
		List<HtmlElement> rows = (List<HtmlElement>) elem
				.getByXPath("tbody//tr");
		HtmlElement td1, td2;
		td1 = rows.get(1).getFirstByXPath("td[2]");
		td2 = rows.get(0).getFirstByXPath("td[3]");
		String startingStation = td1.asText();
		String arrivingStation = td2.asText();

		for (int i = 2; i < rows.size() - 1; i++) {
			td1 = rows.get(i).getFirstByXPath("td[2]");
			td2 = rows.get(i).getFirstByXPath("td[3]");
			cityBusList.addAll(parseCityBusRow(td1.asText(), td2.asText(),
					startingStation, arrivingStation, lineNumber));
		}
		return cityBusList;

	}

	private static List<CityBus> parseCityBusRow(String td1, String td2,
			String startingStation, String arrivingStation, String lineNumber) {
		List<CityBus> cityBuses = new ArrayList<CityBus>();

		List<Time> timings = splitTimings(td1);

		for (Time time : timings) {
			CityBus bus = new CityBus();
			bus.setStartingStation(startingStation);
			bus.setArrivingStation(arrivingStation);
			bus.setStartTime(time);
			bus.setLineNumber(lineNumber);
			cityBuses.add(bus);
		}

		timings = splitTimings(td2);

		for (Time time : timings) {
			CityBus bus = new CityBus();
			bus.setStartingStation(startingStation);
			bus.setArrivingStation(arrivingStation);
			bus.setStartTime(time);
			bus.setLineNumber(lineNumber);
			cityBuses.add(bus);
		}
		return cityBuses;

	}

	private static List<Time> splitTimings(String timing) {
		String[] timeArray = timing.split("/");
		List<Time> returnTimings = new ArrayList<Time>();
		for (String string : timeArray) {
			returnTimings.add(Time.valueOf(standardizeTime(string)));
		}
		return returnTimings;
	}

	private static String standardizeTime(String timeString) {
		return timeString.replace("*", "");

	}

}
