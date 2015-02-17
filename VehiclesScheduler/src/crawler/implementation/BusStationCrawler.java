package crawler.implementation;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;

public class BusStationCrawler {

	public void sendPOST() throws Exception {

		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false); // I think this speeds the thing up
		webClient.getOptions().setRedirectEnabled(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
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

		HtmlTextInput from = page.getElementByName("ctl00$regMainContent$PocetnaTocka");
		from.setValueAttribute("скопје");
		
		HtmlTextInput to = page.getElementByName("ctl00$regMainContent$KrajnaTocka");
		to.setValueAttribute("гевгелија");
		
		HtmlButtonInput button = page.getElementByName("btnBaraj");
		
		HtmlPage page2 = button.click();
		
		String content = page2.asText();
		System.out.println(content);

		webClient.closeAllWindows();
	}

	public static void main(String[] args) {
		BusStationCrawler test = new BusStationCrawler();
		try {
			test.sendPOST();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
