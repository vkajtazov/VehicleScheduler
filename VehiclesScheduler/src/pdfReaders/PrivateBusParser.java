package pdfReaders;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import models.City;
import models.CityBus;
import models.CityBus.REGULARITY;
import crawler.interfaces.BasicCrawlerInterface;

public class PrivateBusParser implements BasicCrawlerInterface<CityBus> {
	private String pathToFile;
	private int oneWaystartRow;
	private int oneWayendRow;
	private int backWaystartRow;
	private int backWayendRow;
	private String firstStation;
	private String secoundStation;
	private CityBus.REGULARITY regularity;
	private String lineNumber;
	private String[] rowsArray;

	public PrivateBusParser(String pathToFile) {
		this.pathToFile = pathToFile;
		rowsArray = PdfReader.getRowsStringsFromPdf(pathToFile);
		regularity = PrivateBusScheduler.getRegularity(pathToFile.split("\\.")[1]);
		lineNumber = rowsArray[1].split("\\.")[1].trim();
		setStartingEndingRows();
	}

	@Override
	public List<CityBus> getAll() {
		ArrayList<CityBus> cityBusList = new ArrayList<CityBus>();
		ArrayList<String> oneWaySplits = parseSchedule(oneWaystartRow,
				oneWayendRow);
		ArrayList<String> backWaySplits = parseSchedule(backWaystartRow,
				backWayendRow);
		cityBusList.addAll(fillCityBus(oneWaySplits, lineNumber, regularity,
				firstStation, secoundStation));
		cityBusList.addAll(fillCityBus(backWaySplits, lineNumber, regularity,
				secoundStation, firstStation));

		return cityBusList;
	}

	public static boolean isWhiteSpaces(String str) {
		 int strLen;
		    if (str == null || (strLen = str.length()) == 0) {
		        return true;
		    }
		    for (int i = 0; i < strLen; i++) {
		        if ((Character.isWhitespace(str.charAt(i)) == false)) {
		            return false;
		        }
		    }
		    return true;
	}

	private void setStartingEndingRows() {
		oneWaystartRow = 2;
		firstStation = rowsArray[3].split("\\s")[1];
		for (int i = 2; i < rowsArray.length - 1; i++) {

			if (isWhiteSpaces(rowsArray[i])) {
				oneWayendRow = i - 1;
				backWaystartRow = i + 1;
				secoundStation = rowsArray[i + 1].split("\\s")[1];
			}
		}
		backWayendRow = rowsArray.length - 1;

	}

	private ArrayList<String> parseSchedule(int start, int end) {
		ArrayList<String> result = new ArrayList<String>();

		for (int i = start; i < end; i++) {
			String[] tokens = rowsArray[i].trim().split("\\s");
			for (String string : tokens) {
				result.add(string);
			}
		}

		return result;
	}

	private Time getTime(String stringTime) {

		String[] time = stringTime.split(",");
		if (time.length <= 1) {
			time = stringTime.split("\\.");
		}
		String hour = null;
		String minutes = null;
		if (time.length == 2) {
			hour = time[0].trim();
			minutes = time[1].trim();
		} else {
			hour = stringTime;
			minutes = "0";

		}
		if (Integer.parseInt(hour) > 24) {
			System.out.println(stringTime);
			int h = Integer.parseInt(hour);
			hour = String.valueOf(h - 100);
		}

		Time returnTime = Time.valueOf(hour + ":" + minutes + ":00");

		return returnTime;
	}

	private List<CityBus> fillCityBus(ArrayList<String> schedules,
			String lineNumber, CityBus.REGULARITY regularity, String first,
			String secound) {

		ArrayList<CityBus> cityBusList = new ArrayList<CityBus>();

		for (int i = 1; i < schedules.size(); i++) {
			CityBus newBus = new CityBus();
			newBus.setStartingStation(first);
			newBus.setArrivingStation(secound);
			newBus.setLineNumber(lineNumber);
			newBus.setRegularity(regularity);
			newBus.setStartTime(getTime(schedules.get(i)));
			newBus.setType(CityBus.TYPE.Private);
			cityBusList.add(newBus);
		}

		return cityBusList;
	}

}
