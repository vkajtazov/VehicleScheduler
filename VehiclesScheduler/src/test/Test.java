package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import crawler.implementation.BtBusStationCrawler;
import locationReaders.LocationReader;
import models.IntercityBus;
import models.Taxi;
import models.Train;
import pdfReaders.PdfReader;
import pdfReaders.PrivateBusScheduler;
import pdfReaders.TrainScheduler;

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

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// TaxiCrawler crawler = new TaxiCrawler();
		// printTaxiList(crawler.getAll());
		// BtBusStationCrawler btCrawler = new BtBusStationCrawler();
		// printIntercityBusList(btCrawler.getAll());
		// OhBusStationCrawler ohCrawler = new OhBusStationCrawler();
		// printIntercityBusList(ohCrawler.getAll());
		//ArrayList<Train>trainList = TrainScheduler.parseAllTrainPdfs();
		//printTrainList(trainList);

		LocationReader.getCityLocation("Гевгелија");
		
		// String[] list =
		// PdfReader.getRowsStringsFromPdf("pdfs/trains/vozen red 14-15.pdf");

		//PrivateBusScheduler scheduler = new PrivateBusScheduler(
		//		"pdfs/trains/vozen red 14-15.pdf", 2, 13, 11);
		//scheduler.getAll();
	
		 /* String[] list =
		  PdfReader.getRowsStringsFromPdf("pdfs/private bus skopje/22.pdf");
		  int i=0; for (String string : list) {
		  System.out.println(i+".\t"+string); i++; }
		 */

	}

}
