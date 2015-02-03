package crawler.implementation;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import models.IntercityBus;
import crawler.interfaces.BasicCrawlerInterface;

public class OhBusStationCrawler implements BasicCrawlerInterface<IntercityBus> {

	private String url = "http://www.galeb.com.mk/?pages=6";

	@Override
	public List<IntercityBus> getAll() {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Elements trElements = doc.select("table[width=100%] tr");
		ArrayList<IntercityBus> busList = new ArrayList<IntercityBus>();

		for (int i = 0; i < trElements.size(); i += 2) {
			IntercityBus bus = new IntercityBus();
			setStartingTime(trElements.get(i), bus);
			setPositions(trElements.get(i+1), bus);
			busList.add(bus);
		}

		return busList;
	}

	private void setStartingTime(Element element, IntercityBus bus) {
		Elements tdElements = element.select("td");
		Time time = Time.valueOf(tdElements.get(0).text() + ":00");
		bus.setStartTime(time);
	}

	private void setPositions(Element element, IntercityBus bus) {
		Elements tdElements = element.select("td");
		String[] cities = tdElements.get(0).text().split("-");
		bus.setStartingCity(cities[0]);
		bus.setArrivingCity(cities[cities.length - 1]);

		ArrayList<String> passingCities = new ArrayList<String>();
		for (int i = 1; i < cities.length - 1; i++) {
			passingCities.add(cities[i]);
		}
		bus.setPassingCities(passingCities);
	}

}
