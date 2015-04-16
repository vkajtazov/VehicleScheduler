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
		regularity = PrivateBusScheduler
				.getRegularity(pathToFile.split("\\.")[1]);
		lineNumber = parseLineNumber(rowsArray[1]);
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

	private String parseLineNumber(String lineString) {
		return lineString.replaceAll("[^0-9]", "");
	}

	private void setStartingEndingRows() {
		oneWaystartRow = 2;

		firstStation = rowsArray[2].split("\\s{3,}")[0];
		for (int i = 2; i < rowsArray.length - 1; i++) {
			if (isWhiteSpaces(rowsArray[i]))
				continue;
			if (isWord(rowsArray[i].split("\\s{3,}")[0])) {
				oneWayendRow = i - 1;
				backWaystartRow = i;
				secoundStation = rowsArray[i].split("\\s{3,}")[0];
			}
		}
		backWayendRow = rowsArray.length - 1;

	}

	private boolean isWord(String str) {
		return str.matches("[a-zA-Z]{0,}[^A-Za-z0-9]{0,}\\.{0,}[a-zA-Z]{0,}");

	}

	private ArrayList<String> parseSchedule(int start, int end) {
		ArrayList<String> result = new ArrayList<String>();

		for (int i = start; i < end; i++) {
			String[] tokens = rowsArray[i].trim().split("\\s{3,}");
			String temp;

			for (int j = 0; j < tokens.length; j++) {
				if (isWord(tokens[j]))
					continue;
				temp = removeDuplicateSeparators(',', tokens[j]);
				temp = removeDuplicateSeparators('.', temp);
				result.add(temp);

			}
		}

		return result;
	}

	private String standardizeTime(String time) {
		return time.replace("o", "0");
	}

	public static String removeDuplicateSeparators(char separator, String str) {

		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == separator) {
				for (int j = i + 1; j < charArray.length; j++) {
					if (charArray[j] == separator)
						charArray[i] = ' ';
				}
			}
		}
		return String.copyValueOf(charArray);
	}

	private Time getTime(String stringTime) {

		String[] time = stringTime.split(",");
		if (time.length <= 1) {
			time = stringTime.split("\\.");
		}
		String hour = null;
		String minutes = null;
		if (time.length == 2) {
			hour = standardizeTime(time[0].trim());
			minutes = standardizeTime(time[1].trim());
		} else {
			hour = standardizeTime(stringTime);
			minutes = "0";

		}

		int h = Integer.parseInt(hour);
		if (h > 24) {
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
			if (schedules.get(i).length() == 0)
				continue;
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
