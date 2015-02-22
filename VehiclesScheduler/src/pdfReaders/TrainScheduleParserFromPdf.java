package pdfReaders;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import traslateText.Translate;
import models.Train;
import crawler.interfaces.BasicCrawlerInterface;

public class TrainScheduleParserFromPdf implements BasicCrawlerInterface<Train> {

	private String pathToFile;
	private HashSet<Integer> skipRows;
	private int startRow;
	private int endRow;
	private int numCols;

	public TrainScheduleParserFromPdf(String pathToFile,
			HashSet<Integer> skipRows, int startRow, int endRow, int numCols) {
		super();
		this.pathToFile = pathToFile;
		this.skipRows = skipRows;
		this.startRow = startRow;
		this.endRow = endRow;
		this.numCols = numCols;
	}

	@Override
	public ArrayList<Train> getAll() {
		ArrayList<Train> result = new ArrayList<Train>();

		ArrayList<String[]> list = parseSchedule(startRow, endRow);
		ArrayList<Train> trainList = getListOfTrainRelation(list,
				list.get(0).length);
		result.addAll(trainList);

		return result;
	}

	private ArrayList<String[]> parseSchedule(int start, int end) {
		String[] array = PdfReader.getRowsStringsFromPdf(pathToFile);
		ArrayList<String[]> result = new ArrayList<String[]>();

		for (int i = start; i < end; i++) {
			if (skipRows.contains(i))
				continue;
			String[] tokens = array[i].split("     ");
			result.add(tokens);
		}
		return result;
	}

	private ArrayList<Train> getListOfTrainRelation(ArrayList<String[]> list,
			int size) {
		// od matrica so raspored kreira site mozni termini za trgnuvanje i
		// pristignuvanje
		Train train = null;
		HashMap<Integer, Train> trainMap = new HashMap<Integer, Train>();
		

		ArrayList<Train> result = new ArrayList<Train>();

		for (int start = 0; start < list.size() - 1; start++) {
			for (int position = 1; position < numCols; position++) {
				for (int end = start + 1; end < list.size(); end++) {
					String[] startSchedule = null;
					String[] endSchedule = null;

					if (list.get(start).length < numCols) {
						startSchedule = tranformRowWithNumCols(
								list.get(start - 1)[0], list.get(start));
					} else {
						startSchedule = list.get(start);
					}
					if (list.get(end).length < numCols) {
						endSchedule = tranformRowWithNumCols(
								list.get(end - 1)[0], list.get(end));
					} else {
						endSchedule = list.get(end);
					}
					if(startSchedule[0].equals(endSchedule[0]))continue;
					train = getTrainBySchedule(startSchedule, endSchedule,
							position);
					
					if (train != null) {
						if(trainMap.containsKey(train.hashCode())){System.out.println(train);}
						else{
							result.add(train);
							trainMap.put(train.hashCode(), train);
						}
					}
					train = null;
				}
			}
		}

		return result;
	}

	private Train getTrainBySchedule(String[] start, String[] end, int position) {
		Train train = setStartingParameters(start, position);
		if (train == null)
			return null;
		train = setArrivingParameters(train, end, position);
		return train;
	}

	private Train setStartingParameters(String[] schedule, int position) {
		// postavuva parametri od stanica kade sto trgnuva
		String start = schedule[0];
		String startTime = schedule[position];

		if (startTime.trim().equals("-"))
			return null;

		String[] splitTime = startTime.trim().split("\\.");

		Time time = Time.valueOf(splitTime[0] + ":" + splitTime[1] + ":00");

		Train train = new Train();
		train.setStartingCity(Translate.translateWord(start));
		train.setStartTime(time);

		return train;
	}

	private String[] tranformRowWithNumCols(String cityName,
			String[] scheduleTimes) {
		String[] array = new String[numCols];

		array[0] = cityName;
		for (int i = 0; i < numCols-1; i++) {
			array[i + 1] = scheduleTimes[i];
		}
		return array;

	}

	private Train setArrivingParameters(Train train, String[] schedule,
			int position) {
		// postavuva parametri od stanica kade sto trgnuva
		String end = schedule[0];

		String endTime = schedule[position];

		if (endTime.trim().equals("-"))
			return null;

		String[] splitTime = endTime.trim().split("\\.");
	//	System.out.println(endTime.trim() + " " + splitTime.length+"   da");
		Time time = Time.valueOf(splitTime[0] + ":" + splitTime[1] + ":00");

		train.setArrivingCity(Translate.translateWord(end));
		train.setArrivingTime(time);

		return train;
	}

	private Train createTrainWithArrivingInformation(String[] niza, int k) {
		// kreira vozen red so informacii za pristignuvanje i vrakja train
		Train train = new Train();
		String temp = niza[k];
		String[] timeSplit = temp.split("\\.");

		Time time = Time.valueOf(timeSplit[0] + ":" + timeSplit[1] + ":00");
		train.setArrivingCity(Translate.translateWord(niza[0]));
		train.setArrivingTime(time);

		return train;
	}

}
