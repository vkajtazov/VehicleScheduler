package crawler.implementation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class BusStationCrawler {

	public void sendPOST() throws Exception {

		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false); // I think this speeds the
														// thing up
		webClient.getOptions().setRedirectEnabled(true);
		webClient.setAjaxController(new AjaxController() {
			@Override
			public boolean processSynchron(HtmlPage page, WebRequest request,
					boolean async) {
				return true;
			}
		});
		webClient.getCookieManager().setCookiesEnabled(true);

		HtmlPage page = null;
		try {
			System.out.println("Querying");
			page = webClient.getPage("http://liniskiprevoz.gov.mk/search.aspx");
			System.out.println("Success");
		} catch (final FailingHttpStatusCodeException e) {
			System.out.println("One");
			e.printStackTrace();
		} catch (final MalformedURLException e) {
			System.out.println("Two");
			e.printStackTrace();
		} catch (final IOException e) {
			System.out.println("Three");
			e.printStackTrace();
		} catch (final Exception e) {
			System.out.println("Four");
			e.printStackTrace();
		}

		HtmlTextInput from = page
				.getElementByName("ctl00$regMainContent$PocetnaTocka");
		from.click();
		from.setValueAttribute("скопје");

		HtmlTextInput to = page
				.getElementByName("ctl00$regMainContent$KrajnaTocka");
		to.click();
		to.setValueAttribute("велес");

		HtmlButtonInput button = page.getElementByName("btnBaraj");

		HtmlPage page2 = button.click();

		webClient.waitForBackgroundJavaScript(10 * 1000);

		// Iterate on pages
		HtmlElement element = page2
				.getFirstByXPath("/html/body/form/div[5]/div[1]/table/tbody/tr[3]/td/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td[7]");

		int counter = 9;

		while (element != null) {
			page2 = element.click();
			webClient.waitForBackgroundJavaScript(10 * 1000);
			System.out.println(page2.asText());
			// proverka dali e stignato do kraj (ja zima strelkata kako element)
			element = page2
					.getFirstByXPath("/html/body/form/div[5]/div[1]/table/tbody/tr[3]/td/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td["
							+ counter + "]/img");
			if (element != null)
				break;
			element = page2
					.getFirstByXPath("/html/body/form/div[5]/div[1]/table/tbody/tr[3]/td/table/tbody/tr/td/div/table/tbody/tr/td/table/tbody/tr/td["
							+ counter + "]");
			counter += 2;
		}

		webClient.closeAllWindows();
	}

	public static ArrayList<String> getAllCities() throws IOException {
		char [] alphabet = new char[] { 'а', 'б', 'в', 'г', 'д', 'ѓ', 'е',
				'ж', 'з', 'ѕ', 'и', 'ј', 'к', 'л', 'љ', 'м', 'н', 'њ', 'о',
				'п', 'р', 'с', 'т', 'ќ', 'у','ф','х','ц','ч','џ','ш'};
		ArrayList<String> cities = new ArrayList<String>();

		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false); // I think this speeds the
														// thing up
		webClient.getOptions().setRedirectEnabled(true);
		webClient.setAjaxController(new AjaxController() {
			@Override
			public boolean processSynchron(HtmlPage page, WebRequest request,
					boolean async) {
				return true;
			}
		});
		webClient.getCookieManager().setCookiesEnabled(true);

		HtmlPage page = null;
		try {
			System.out.println("Querying");
			page = webClient.getPage("http://liniskiprevoz.gov.mk/search.aspx");
			System.out.println("Success");
		} catch (final FailingHttpStatusCodeException e) {
			System.out.println("One");
			e.printStackTrace();
		} catch (final MalformedURLException e) {
			System.out.println("Two");
			e.printStackTrace();
		} catch (final IOException e) {
			System.out.println("Three");
			e.printStackTrace();
		} catch (final Exception e) {
			System.out.println("Four");
			e.printStackTrace();
		}

		HtmlTextInput from = page
				.getElementByName("ctl00$regMainContent$PocetnaTocka");
		page = from.click();

		for (char alpha : alphabet) {
			
		from.type(alpha);
		webClient.waitForBackgroundJavaScript(10 * 1000);
		HtmlElement element = page.getFirstByXPath("/html/body/ul[1]");
		from.setValueAttribute("");
		cities.addAll(parseCityNames(element.asText()));
		}
		
		return cities;

	}
	
	private  static ArrayList<String> parseCityNames (String element){
		ArrayList<String>cities = new ArrayList<String>();
		String[] array  = element.split("\n");
		for (String string : array) {
			cities.add(string);
		}
		return cities;
	}

	public static void main(String[] args) {
		BusStationCrawler test = new BusStationCrawler();
		
		
		try {
			// test.sendPOST();
			test.getAllCities();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
