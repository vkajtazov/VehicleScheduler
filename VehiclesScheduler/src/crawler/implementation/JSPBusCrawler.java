package crawler.implementation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import models.CityBus;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JSPBusCrawler {

	private static String url = "http://jsp.com.mk/VozenRed.aspx";

	private static HtmlPage sedPost() {

		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);

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
			page = webClient.getPage(url);
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
		return page;
	}

	private static List<HtmlElement> getAllLines() {

		HtmlPage firstPage = sedPost();
		List<HtmlElement> inputElements = (List<HtmlElement>) firstPage
				.getByXPath("//input[@type='image'][@vspace='1']");
		return inputElements;
	}

	private static List<HtmlPage> getAllHtmlPages(List<HtmlElement> elements)
			throws IOException {
		for (HtmlElement htmlElement : elements) {
			HtmlPage page = htmlElement.click();

			HtmlInput element = page
					.getFirstByXPath("//input[@src='images/ico_calendar.gif']");

			page = (HtmlPage) element.click();

			page = (HtmlPage) element.setValueAttribute("29.04.2015");
			System.out.println(page.asText());
			// System.out.println(p.asXml());
		}
		return null;
	}

	public static List<CityBus> getAllBusLines() throws IOException {
		List<HtmlElement> elements = getAllLines();
		List<HtmlPage> returnList = new ArrayList<HtmlPage>();

		for (int i=40;i<41;i++) {
			HtmlPage page = elements.get(i).click();
			returnList.add(page);
		}

		return JSPBusParser.parseCityBuses(returnList);

	}

}
