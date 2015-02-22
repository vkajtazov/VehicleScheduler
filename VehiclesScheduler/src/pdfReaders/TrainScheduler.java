package pdfReaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import models.Train;

public class TrainScheduler {

	private static String path = "train schedule.txt";

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static ArrayList<Train> parseAllTrainPdfs() {
		ArrayList<String> list = null;
		ArrayList<Train> result = new ArrayList<Train>();
		try {
			list = readFromDirectoriesNames();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String string : list) {
			result.addAll(getTrainSchedule(string));
		}
		return result;
	}

	private static ArrayList<Train> getTrainSchedule(String string) {
		String[] parseStrings = string.split(";");
		int startRow = Integer.parseInt(parseStrings[1]);
		int endRow = Integer.parseInt(parseStrings[2]);
		int numCols = Integer.parseInt(parseStrings[3]);
		String[] parseSkipRows = parseStrings[4].split(",");
		HashSet<Integer> skipRows = new HashSet<Integer>();
		for (String skipRow : parseSkipRows) {
			skipRows.add(Integer.parseInt(skipRow));
		}
		TrainScheduleParserFromPdf schedule = new TrainScheduleParserFromPdf(
				parseStrings[0], skipRows, startRow, endRow, numCols);

		return schedule.getAll();
	}

	private static ArrayList<String> readFromDirectoriesNames()
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));

		ArrayList<String> resultList = new ArrayList<String>();
		try {

			String line = br.readLine();

			while (line != null) {
				resultList.add(line);
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return resultList;
	}

}
