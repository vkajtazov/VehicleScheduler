package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

public class TestForInternCity {

	public static void main(String[] args) throws IOException {
		try {
			String webPage = "http://api.liniskiprevoz.gov.mk/vozniredovi?datefrom=2013-04-01&type=json";
			String name = "api.liniskiprevoz";
			String password = "asdh^$jhgFD334d$%";

			String authString = name + ":" + password;
			System.out.println("auth string: " + authString);
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			System.out.println("Base64 encoded auth string: " + authStringEnc);

			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic "
					+ authStringEnc);

			Scanner scan = new Scanner(urlConnection.getInputStream());
			String str = new String();

			while (scan.hasNext())
				str += scan.nextLine();
			scan.close();

			// build a JSON object
			JSONObject obj = new JSONObject(str);
			if (!obj.getString("status").equals("OK")) {
			}
			//System.out.println(obj.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
