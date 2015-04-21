package crawler.implementation;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import models.CityBus;
import pdfReaders.PrivateBusParser;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JSPBusParser {

	public static List<CityBus> parseCityBuses(List<HtmlPage> pages) {
		List<CityBus> cityBusList = new ArrayList<CityBus>();

		for (HtmlPage page : pages) {
			String lineNumber = parseCityBusLineNumber(page);
			cityBusList.addAll(parseCityBusSchedule(page, lineNumber));
		}
		return cityBusList;
	}

	private static String parseCityBusLineNumber(HtmlPage page) {
		HtmlElement elem = page.getFirstByXPath("//table[@width='250']");
		HtmlElement lineNumber = elem.getFirstByXPath("tbody//strong");
		return lineNumber.asText();
	}

	private static List<CityBus> parseCityBusSchedule(HtmlPage page,
			String lineNumber) {
		List<CityBus> cityBusList = new ArrayList<CityBus>();

		HtmlElement elem = page
				.getFirstByXPath("//table[@width='480'][@cellpadding='2']");
		List<HtmlElement> rows = (List<HtmlElement>) elem
				.getByXPath("tbody//tr");
		HtmlElement td1, td2;
		td1 = rows.get(1).getFirstByXPath("td[2]");
		td2 = rows.get(1).getFirstByXPath("td[3]");
		String startingStation = td1.asText();
		String arrivingStation = td2.asText();

		for (int i = 3; i < rows.size() - 1; i++) {
			td1 = rows.get(i).getFirstByXPath("td[2]");
			td2 = rows.get(i).getFirstByXPath("td[3]");
			cityBusList.addAll(parseCityBusRow(td1.asText(), td2.asText(),
					startingStation, arrivingStation, lineNumber));
		}
		return cityBusList;

	}

	private static List<CityBus> parseCityBusRow(String td1, String td2,
			String firstStation, String secondStation, String lineNumber) {
		List<CityBus> cityBuses = new ArrayList<CityBus>();

		List<Time> timings = splitTimings(td1);
		for (Time time : timings) {
			CityBus bus = new CityBus();
			bus.setType(CityBus.TYPE.Public);
			bus.setStartingStation(firstStation);
			bus.setArrivingStation(secondStation);
			bus.setStartTime(time);
			bus.setLineNumber(lineNumber);
			cityBuses.add(bus);
		}

		timings = splitTimings(td2);

		for (Time time : timings) {
			CityBus bus = new CityBus();
			bus.setStartingStation(secondStation);
			bus.setArrivingStation(firstStation);
			bus.setStartTime(time);
			bus.setLineNumber(lineNumber);
			cityBuses.add(bus);
		}
		return cityBuses;

	}

	private static boolean isAlphaInString(String str) {
		char[] charArray = str.toCharArray();
		for (char c : charArray) {
			if (Character.isAlphabetic(c))
				return true;
		}
		return false;

	}

	private static List<Time> splitTimings(String timing) {
		String[] timeArray = timing.split("/");
		List<Time> returnTimings = new ArrayList<Time>();
		for (int i = 0; i < timeArray.length; i++) {
			if (!PrivateBusParser.isWhiteSpaces(timeArray[i])) {

				String[] splitArray = timeArray[i].split(":");

				String time = null;
				if (splitArray.length == 1) {
					time = standardizeTime(timeArray[i]) + ":"
							+ standardizeTime(timeArray[i + 1]) + ":00";
					i++;
				} else if (splitArray.length == 2) {

					time = standardizeTime(timeArray[i]) + ":00";
				} else if (splitArray.length == 4) {
					time = standardizeTime(splitArray[0] + ":" + splitArray[1]+":00");
					String time2 = standardizeTime(splitArray[2]+":"+splitArray[3]+":00");
					returnTimings.add(Time.valueOf(time2));

				}
				returnTimings.add(Time.valueOf(time));
			}
		}

		return returnTimings;
	}

	private static String standardizeTime(String timeString) {
		timeString = timeString.trim();
		/*
		 * for (int i=0;i<charArray.length;i++) { if
		 * (Character.isAlphabetic(charArray[i]) || charArray[i]=='.') {
		 * System.out.println(charArray[i]); charArray[i] = ' '; } } tmp =
		 * String.copyValueOf(charArray); tmp = tmp.replace(" ", "");
		 * System.out.println(timeString + " " + tmp);
		 */
		if (isAlphaInString(timeString)) {
			String[] list = timeString.split(" ");
			// System.out.print(list.length +" "+ timeString);
			for (String string : list) {
				// System.out.println(string);
			}
			timeString = list[0];
			// System.out.println("timeString"+timeString);
		} else {
			timeString = timeString.replace(" ", "");
		}
		timeString = timeString.replace("*", "");
		// System.out.println(" "+timeString);
		return timeString;

	}

	private static CityBus.REGULARITY getRegularity(Date d) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY) {
			return CityBus.REGULARITY.Saturday;
		} else if (day == Calendar.SUNDAY) {
			return CityBus.REGULARITY.Sunday;
		}
		return CityBus.REGULARITY.EveryDay;
	}

}
