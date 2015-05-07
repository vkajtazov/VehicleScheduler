package interncityBusLines;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;

import models.Line;
import models.Station;
import models.SubLine;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

public class InternCityBuslineGetter {

	private static String urlRequest = "http://api.liniskiprevoz.gov.mk/vozniredovi?datefrom=2013-04-01&type=json";
	private static String name = "api.liniskiprevoz";
	private static String password = "asdh^$jhgFD334d$%";

	private static JSONArray getSchedule() {
		try {
			String authString = name + ":" + password;
			System.out.println("auth string: " + authString);
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			System.out.println("Base64 encoded auth string: " + authStringEnc);

			URL url = new URL(urlRequest);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic "
					+ authStringEnc);
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			result = result.trim();
			result = result.substring(1, result.length() - 1);
			result = result.replace("\\", "");
			JSONObject obj = new JSONObject(result);

			return obj.getJSONObject("VozniRedovi").getJSONArray("VozenRed");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void get() {
		JSONArray arr = getSchedule();
		Line line = new Line();
		setLineInfo(line, arr.getJSONObject(0));
		System.out.println(line.toString());
	}

	private static void setLineInfo(Line line, JSONObject obj) {
		String shortName = obj.get("@Ime").toString();
		String transporter = obj.get("@FirmaIme").toString();
		String distance = obj.get("@RastojaniePocetnaKrajna").toString();
		line.setRouteShortName(shortName);
		line.setTransporter(transporter);
		line.setDistance(distance);
		setSubLines(line, obj);
	}

	private static void setSubLines(Line line, JSONObject obj) {
		JSONArray array = obj.getJSONObject("DefinicijaNaVozniotRed")
				.getJSONArray("VozenRedDef");
		int i = 1;
		String direction;
		do {
			direction = array.getJSONObject(i).get("@Nasoka").toString();
			if (direction.equals("1")) {
				Station start = getStation(array.getJSONObject(i - 1));
				Station end = getStation(array.getJSONObject(i));
				SubLine subline = new SubLine();
				subline.setStartingStation(start);
				subline.setArrivingStation(end);
				setTraveledTIme(obj, subline, i);
				line.addSubLine(subline);
				i++;
			} else {
				break;
			}
		} while (true);
		line.setStartStation(getStationfromIndex(obj, 0));
		line.setArrivingStation(getStationfromIndex(obj, array.length() - 1));
		line.setOneWayStartTime(getTimeFromIndex(obj, 0));
		line.setOneWayArriveTime(getTimeFromIndex(obj, i - 1));
		line.setBackWayStartTime(getTimeFromIndex(obj, array.length() - 1));
		line.setBackWayArriveTime(getTimeFromIndex(obj, i));
	}

	private static Station getStationfromIndex(JSONObject obj, int index) {
		JSONArray array = obj.getJSONObject("DefinicijaNaVozniotRed")
				.getJSONArray("VozenRedDef");
		return getStation(array.getJSONObject(index));
	}

	private static Time getTimeFromIndex(JSONObject obj, int index) {
		JSONArray array = obj.getJSONObject("DefinicijaNaPoagjanja")
				.getJSONObject("PoaganjeDef")
				.getJSONObject("VreminjaNaPoaganje")
				.getJSONArray("PoaganjeVreme");
		String timeString = array.getJSONObject(index).get("@VremeNaPoaganje")
				.toString();
		return Time.valueOf(timeString.split("T")[1]);
	}

	private static void setTraveledTIme(JSONObject obj, SubLine subline, int i) {
		JSONArray array = obj.getJSONObject("DefinicijaNaPoagjanja")
				.getJSONObject("PoaganjeDef")
				.getJSONObject("VreminjaNaPoaganje")
				.getJSONArray("PoaganjeVreme");
		String startTime = array.getJSONObject(i - 1).get("@VremeNaPoaganje")
				.toString();
		String endTime = array.getJSONObject(i).get("@VremeNaPoaganje")
				.toString();
		int traveledTime = parseTraveledMinutesFromString(endTime)
				- parseTraveledMinutesFromString(startTime);
		subline.setTraveledTime(parseTimeFromMinutes(traveledTime));

	}

	private static Time parseTimeFromMinutes(int minutes) {
		int hour = minutes / 60;
		minutes = minutes % 60;
		return Time.valueOf(hour + ":" + minutes + ":00");

	}

	private static int parseTraveledMinutesFromString(String parseTime) {
		String[] timeString = parseTime.split("T")[1].split(":");
		int hour = Integer.valueOf(timeString[0]);
		int minutes = Integer.valueOf(timeString[1]);
		return hour * 60 + minutes;
	}

	private static Station getStation(JSONObject obj) {
		String stationName = obj.get("@Ime").toString();
		Station station = new Station();
		station.setStationName(stationName);
		return station;
	}

}
