package test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import crawler.implementation.JSPBusCrawler;
import crawler.implementation.JSPBusParser;
import models.CityBus;
import models.IntercityBus;
import models.Taxi;
import models.Train;

public class Test {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */

	public static void printTrainList(List<Train> list)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("trains.txt", "UTF-8");

		for (Train train : list) {
			writer.print(train.toString());
			writer.println("------------------------------------------");
		}
		writer.close();
	}

	public static void printTaxiList(List<Taxi> list)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("taxies.txt", "UTF-8");

		for (Taxi taxi : list) {
			writer.print(taxi.toString());
			writer.println("------------------------------------------");
		}
		writer.close();
	}

	public static void printIntercityBusList(List<IntercityBus> list)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("bus.txt", "UTF-8");

		for (IntercityBus bus : list) {
			writer.print(bus.toString());
			writer.println("------------------------------------------");
		}
		writer.close();
	}
	
	public static void printCityBusList(List<CityBus> list)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("cityBus.txt", "UTF-8");

		for (CityBus bus : list) {
			writer.print(bus.toString());
			writer.println("------------------------------------------");
		}
		writer.flush();
		writer.close();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// TaxiCrawler crawler = new TaxiCrawler();
		// printTaxiList(crawler.getAll());
		// BtBusStationCrawler btCrawler = new BtBusStationCrawler();
		// printIntercityBusList(btCrawler.getAll());
		// OhBusStationCrawler ohCrawler = new OhBusStationCrawler();
		// printIntercityBusList(ohCrawler.getAll());
		// ArrayList<Train>trainList = TrainScheduler.parseAllTrainPdfs();
		// printTrainList(trainList);

		// LocationReader.getCityLocation("Гевгелија");

		// String[] list =
		// PdfReader.getRowsStringsFromPdf("pdfs/trains/vozen red 14-15.pdf");

		/*
		PrivateBusScheduler scheduler = new PrivateBusScheduler();
		printCityBusList(scheduler.parseAllBusses());*/
		
		printCityBusList(JSPBusCrawler.getAllBusLines());
		
	/*	String[] list = PdfReader
				.getRowsStringsFromPdf("pdfs/private bus skopje/9.Sunday.pdf");
		int i = 0;
		for (String string : list) {
			System.out.print(i + ".\t" + string);
			i++;
		}*/

	}

}
