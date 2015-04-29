package test;

import java.io.IOException;

import pdfReaders.PrivateBusScheduler;
import sendPostToServer.PostSender;

public class TestForBuslines {
	public static void main(String[] args) throws IOException {
		// BusLinesGetter.firstMethod();
		PrivateBusScheduler scheduler = new PrivateBusScheduler();
		PostSender.sendPostToServer(scheduler.parseAllBusses());
	}

}
