package busLines;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class BusLinesGetter {

	private static String baseUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=";
	private static String dest = "&destination=";
	private static String optional = "&sensor=false&language=mk&mode=transit&alternatives=true";
	private static String key = "&key=AIzaSyCgJMmeJJHwhlaTvXQkD9LDD0RA3q25SR4";

	private static JSONObject getDestinationLocation(String start, String end)
			throws IOException {
		// build a URL
		String urlRequest = baseUrl + start + dest + end + optional + key;
		System.out.println(urlRequest);
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

	public static void firstMethod(String start, String end) throws IOException {

		JSONObject obj = getDestinationLocation(start, end);
		JSONArray arj = obj.getJSONArray("routes");
		arj = arj.getJSONObject(0).getJSONArray("legs");
		arj = arj.getJSONObject(0).getJSONArray("steps");
		for (int i = 0; i < arj.length(); i++) {
			System.out.println(arj.get(i).toString());

		}
	}
}
