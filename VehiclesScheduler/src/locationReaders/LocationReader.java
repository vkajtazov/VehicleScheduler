package locationReaders;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import models.City;
import models.Municipality;

import org.json.JSONObject;

public class LocationReader {

	private static String baseUrl = "http://maps.google.com/maps/api/geocode/json?"
			+ "sensor=false&address=";

	public static City getCityLocation(String cityName) throws IOException {
		JSONObject obj = getDestinationLocation(cityName);
		return fillCityObject(obj);

	}

	public static Municipality getMunicipality(String municipality)
			throws IOException {
		JSONObject obj = getDestinationLocation(municipality);
		return fillMunicipality(obj);
	}

	private static JSONObject getDestinationLocation(String destination)
			throws IOException {
		// build a URL
		String urlRequest = baseUrl + URLEncoder.encode(destination, "UTF-8");
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

	private static City fillCityObject(JSONObject obj) {
		City city = new City();
		JSONObject res = obj.getJSONArray("results").getJSONObject(0);
		JSONObject loc = res.getJSONObject("geometry")
				.getJSONObject("location");

		city.setCityLatitude(loc.getDouble("lat"));
		city.setCityLongitude(loc.getDouble("lng"));

		return city;
	}

	private static Municipality fillMunicipality(JSONObject obj) {
		Municipality municipality = new Municipality();
		JSONObject res = obj.getJSONArray("results").getJSONObject(0);
		JSONObject loc = res.getJSONObject("geometry")
				.getJSONObject("location");

		municipality.setMunicipalityLatitude(loc.getDouble("lat"));
		municipality.setMunicipalityLongitude(loc.getDouble("lng"));

		return municipality;

	}
}
