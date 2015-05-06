package interncityBusLines;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

public class InternCityBuslineGetter {

	private static String urlRequest = "http://api.liniskiprevoz.gov.mk/vozniredovi?datefrom=2013-04-01&type=json";

	public static JSONObject getDestinationLocation()
			throws IOException {
		String name = "api.liniskiprevoz";
		String password = "asdh^$jhgFD334d$%";

		String authString = name + ":" + password;
		System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		
		// build a URL
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
		System.out.println(obj.toString());
		return obj;
	}

}
