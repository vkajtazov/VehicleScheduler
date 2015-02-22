package pdfReaders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import models.CityBus;
import crawler.interfaces.BasicCrawlerInterface;

public class PrivateBusScheduler implements BasicCrawlerInterface<CityBus>{
	private String pathToFile;
	private int startRow;
	private int endRow;

	public PrivateBusScheduler(String pathToFile, int startRow, int endRow) {
		super();
		this.pathToFile = pathToFile;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	@Override
	public List<CityBus> getAll() {
		ArrayList<String[]> rowsSplits = parseSchedule(startRow, endRow);
		return null;
	}
	
	private ArrayList<String[]> parseSchedule(int start, int end) {
		String[] rowsArray = PdfReader.getRowsStringsFromPdf(pathToFile);
		ArrayList<String[]> result = new ArrayList<String[]>();

		for (int i = start; i < end; i++) {
			String[] tokens = rowsArray[i].split("     ");
			result.add(tokens);
		}
		return result;
	}
	
	

}
