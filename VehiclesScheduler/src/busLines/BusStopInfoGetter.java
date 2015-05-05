package busLines;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import models.Station;

public class BusStopInfoGetter {

	public static String baseUrl = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyCgJMmeJJHwhlaTvXQkD9LDD0RA3q25SR4&placeid=";

	private static String getStationNameByPlaceId(String placeId)
			throws IOException {
		// build a URL
		String urlRequest = baseUrl + placeId;
		URL url = new URL(urlRequest);

		// read from the URL
		Scanner scan = new Scanner(url.openStream());
		String str = new String();
		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();

		// build a JSON object
		JSONObject obj = new JSONObject(str);
		if (!obj.getString("status").equals("OK"))
			return null;
		System.out.println(obj.getJSONObject("result").get("name"));
		
		return obj.toString();
	}

	public static void addStationName(List<Station> stationList) throws IOException {
		for (Station station : stationList) {
			station.setStationName(getStationNameByPlaceId(station.getPlaceID()));
		}
	}

}
