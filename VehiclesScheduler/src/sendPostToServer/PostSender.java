package sendPostToServer;

import java.io.IOException;
import java.util.List;

import models.CityBus;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class PostSender {

	public static String convertCityBusToJson(CityBus cityBus)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(cityBus);
		return json;
	}

	private static String convertCityBusListToJson(List<CityBus> busList)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(busList);
		return json;
	}

	public static void sendPostToServer(List<CityBus> busList)
			throws JsonGenerationException, JsonMappingException, IOException {
		String jsonString = convertCityBusListToJson(busList);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			HttpPost request = new HttpPost(
					"http://localhost:9966/transportMK/data/rest/insert/cityBusList");
			StringEntity params = new StringEntity(jsonString);
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			httpClient.execute(request);
			// handle response here...
		} catch (Exception ex) {
			// handle exception here
		} finally {
			httpClient.close();
		}
	}

}
