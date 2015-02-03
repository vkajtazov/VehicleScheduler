package crawler.implementation;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.IntercityBus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.interfaces.BasicCrawlerInterface;

public class BtBusStationCrawler implements BasicCrawlerInterface<IntercityBus> {

	private String url = "http://www.transkop.mk/index.php?cat=vozenred&id=celosen";

	@Override
	public List<IntercityBus> getAll() {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		ArrayList<IntercityBus> busList = new ArrayList<IntercityBus>();
		Elements trElements = doc.select("tr");
		for (int i = 1; i < trElements.size(); i++) {
			IntercityBus bus = getBusFromTr(trElements.get(i));
			busList.add(bus);
		}
		return busList;
	}

	private IntercityBus getBusFromTr(Element trElement) {
		IntercityBus bus = new IntercityBus();
		Elements tdElements = trElement.select("td");
		bus.setStartTime(getTime(tdElements.get(0)));
		bus.setStartingCity("Битола");
		bus.setArrivingCity(capitalizedStrings(tdElements.get(1).text()));
		bus.setPassingCities(getPassingCities(tdElements.get(2)));
		bus.setRegularity(tdElements.get(3).text());
		bus.setTrasporter(tdElements.get(4).text());

		return bus;
	}
	
	private String capitalizedStrings (String city){
		String first = Character.toString(city.charAt(0)).toUpperCase();
		String second = city.substring(1).toLowerCase(); 
		return first+second;
	}

	private Time getTime(Element elem) {
		Time time = Time.valueOf(elem.text()+":00");
		return time;
	}

	private List<String> getPassingCities(Element element) {
		String passsingCities = element.text();
		String[] passing = passsingCities.split(", ");
		ArrayList<String> cities = new ArrayList<String>();
		for (String string : passing) {
			cities.add(string);
		}
		return cities;
	}

}
