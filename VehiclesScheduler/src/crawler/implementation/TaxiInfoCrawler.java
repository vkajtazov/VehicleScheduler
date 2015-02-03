package crawler.implementation;

import java.io.IOException;
import java.util.StringTokenizer;

import models.Taxi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TaxiInfoCrawler {

	private static Document doc;
	private static String url = "http://zk.mk/";

	private static void init(String path) {
		try {
			doc = Jsoup.connect(url + path).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Taxi getTaxi(String path) {

		init(path);
		Taxi taxi = new Taxi();
		System.out.println(getTaxiName());
		taxi.setTaxiName(getTaxiName());
		taxi.setTaxiLocation(getTaxiLocation());
		taxi.setTaxiAddress(getTaxiAddress());
		taxi.addTaxiPhone(getTaxiFirstPhone());
		addAnotherPhones(taxi);

		return taxi;
	}

	private static String getTaxiName() {
		Elements taxiElements = doc.select("a.companyname");
		Element elem = taxiElements.get(0);
		return elem.text();
	}

	private static String getTaxiAddress() {
		Elements taxiElements = doc.select("li[itemprop=streetAddress]");
		if (taxiElements.size() > 0) {
			Element elem = taxiElements.get(0);
			String[] array = elem.text().split(": ");
			return array[1];
		}
		return "";
	}

	private static String getTaxiFirstPhone() {
		Elements taxiElements = doc.select("a[itemprop=telephone]");
		Element elem = taxiElements.get(0);
		return elem.text();
	}

	private static String getTaxiLocation() {
		Elements taxiElements = doc.select("li[itemprop=addressLocality]");
		Element elem = taxiElements.get(0);
		String[] array = elem.text().split(": ");
		return array[1];
	}

	private static boolean checkForAnotherPhone(Element elem) {
		StringTokenizer tokenizer = new StringTokenizer(elem.text());
		while (tokenizer.hasMoreTokens()) {
			if (tokenizer.nextToken().equals("Телефон"))
				return true;
		}
		return false;
	}

	private static void addAnotherPhones(Taxi taxi) {
		Elements taxiElements = doc.select(".details li");
		for (Element element : taxiElements) {
			if (checkForAnotherPhone(element)) {
				String[] array = element.text().split(": ");
				taxi.addTaxiPhone(array[1]);
			}
		}
	}

}
