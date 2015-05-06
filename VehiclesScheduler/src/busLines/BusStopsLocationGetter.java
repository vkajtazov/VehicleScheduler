package busLines;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Station;

import org.json.JSONArray;
import org.json.JSONObject;

public class BusStopsLocationGetter {

	public static String baseUrl = "https://maps.googleapis.com/maps/api/place/radarsearch/json?location=";
	public static String parameters = "&types=bus_station&language=mk&key=AIzaSyCgJMmeJJHwhlaTvXQkD9LDD0RA3q25SR4&radius=";

	public static JSONObject getJsonObj(String location, String radius)
			throws IOException {
		// build a URL
		String urlRequest = baseUrl + location + parameters + radius;
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
		return obj;
	}

	public static List<Station> get() throws IOException {
		// Skopje center location
		String location = "41.996922,21.427637";
		// 15 km radius
		String radius = "15000";
		JSONObject obj = getJsonObj(location, radius);
		JSONArray arr = obj.getJSONArray("results");
		ArrayList<Station> stationList = new ArrayList<Station>();
		for (int i = 0; i < arr.length(); i++) {
			JSONObject stationJSON = arr.getJSONObject(i)
					.getJSONObject("geometry").getJSONObject("location");

			Station station = new Station();
			station.setStationLatitude(Double.valueOf(stationJSON.get("lat")
					.toString()));
			station.setStationLongitude(Double.valueOf(stationJSON.get("lng")
					.toString()));
			station.setPlaceID(arr.getJSONObject(i).get("place_id").toString());
			stationList.add(station);
		}
		return stationList;
	}
}
