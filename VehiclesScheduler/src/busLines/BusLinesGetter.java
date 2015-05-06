package busLines;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Line;
import models.Station;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

public class BusLinesGetter {

	private static String baseUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=";
	private static String dest = "&destination=";
	private static String optional = "&sensor=false&language=mk&mode=transit&alternatives=true";
	private static String key = "&key=AIzaSyCgJMmeJJHwhlaTvXQkD9LDD0RA3q25SR4";

	private static JSONObject getDestinationLocation(String start, String end)
			throws IOException {
		
		String name = "api.liniskiprevoz";
		String password = "asdh^$jhgFD334d$%";

		String authString = name + ":" + password;
		System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		
		// build a URL
		String urlRequest = baseUrl + start + dest + end + optional + key;
		System.out.println(urlRequest);
		URL url = new URL(urlRequest);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

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

	public static List<Line> parseRoutes(String start, String end)
			throws IOException {
		List<Line> listStation = new ArrayList<Line>();
		JSONObject obj = getDestinationLocation(start, end);
		if (obj == null)
			return listStation;
		JSONArray arj = obj.getJSONArray("routes");
		Line line = null;
		for (int i = 0; i < arj.length(); i++) {
			line = parseRoute(arj.getJSONObject(i));
			if (line != null) {
				listStation.add(line);
			}
		}
		return listStation;
	}

	public static Line parseRoute(JSONObject jsonObj) {
		JSONArray arr = jsonObj.getJSONArray("legs");
		JSONObject tempJson;
		arr = arr.getJSONObject(0).getJSONArray("steps");

		for (int i = 0; i < arr.length(); i++) {
			String travelMode = arr.getJSONObject(i).get("travel_mode")
					.toString();

			if (travelMode.equals("TRANSIT")) {
				String numStops = arr.getJSONObject(i)
						.getJSONObject("transit_details").get("num_stops")
						.toString();
				if (numStops.equals("1")) {
					return parseTransitInfo(arr.getJSONObject(i));
				}
			} else {
				return null;
			}
		}
		return null;
	}

	public static Line parseTransitInfo(JSONObject jsonObj) {
		Line line = new Line();
		String distance = jsonObj.getJSONObject("distance").get("text")
				.toString();
		String time = jsonObj.getJSONObject("duration").get("text").toString();
		String arrivalstop = jsonObj.getJSONObject("transit_details")
				.getJSONObject("arrival_stop").get("name").toString();
		String arrivallat = jsonObj.getJSONObject("transit_details")
				.getJSONObject("arrival_stop").getJSONObject("location")
				.get("lat").toString();
		String arrivallng = jsonObj.getJSONObject("transit_details")
				.getJSONObject("arrival_stop").getJSONObject("location")
				.get("lng").toString();
		String departurestop = jsonObj.getJSONObject("transit_details")
				.getJSONObject("departure_stop").get("name").toString();
		String departurelat = jsonObj.getJSONObject("transit_details")
				.getJSONObject("departure_stop").getJSONObject("location")
				.get("lat").toString();
		String departurelng = jsonObj.getJSONObject("transit_details")
				.getJSONObject("departure_stop").getJSONObject("location")
				.get("lng").toString();

		Station start = new Station();
		start.setStationLatitude(Double.valueOf(departurelat));
		start.setStationLongitude(Double.valueOf(departurelng));
		start.setStationName(departurestop);
		Station end = new Station();
		end.setStationName(arrivalstop);
		end.setStationLatitude(Double.valueOf(arrivallat));
		end.setStationLongitude(Double.valueOf(arrivallng));
		end.setStationName(arrivalstop);

		line.setArrivingStation(end);
		line.setStartingStation(start);
		line.setKmTraveled(distance);
		String minutes = time.split(" ")[0];
		Time traveledTime = Time.valueOf("00:" + minutes + ":00");
		line.setTraveledTime(traveledTime);
		return line;
	}
}
