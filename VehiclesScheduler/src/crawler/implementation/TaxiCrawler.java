package crawler.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Taxi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.interfaces.BasicCrawlerInterface;

public class TaxiCrawler implements BasicCrawlerInterface<Taxi> {

	private String url = "http://zk.mk/search/?what=taksi+kompanii";

	public Elements pageElements(int skip) throws IOException {
		String urlPath = url + "&skip=" + skip;
		Document doc = Jsoup.connect(urlPath).get();
		Elements taxiElements = doc.select("a.companyname");

		return taxiElements;
	}
	
	public Elements getAllElements () {
		int skip = 0;
		Elements elementList = new Elements();
		Elements pageElements;
		do {
			try {
				pageElements = pageElements(skip);
				elementList.addAll(pageElements);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			skip+=20;
		}
		while(pageElements.size()>0);
		
		return elementList;
	}

	@Override
	public List<Taxi> getAll() {
		Elements elements = getAllElements();
		ArrayList<Taxi> taxiList = new ArrayList<Taxi>();
		
		for (Element element : elements) {
			Taxi tempTaxi = TaxiInfoCrawler.getTaxi(element.attr("href"));
			taxiList.add(tempTaxi);
		}
		
	return taxiList;
	}
}
