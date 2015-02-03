package crawler.interfaces;

import java.util.List;

import models.VehicleI;

public interface BasicCrawlerInterface<E extends VehicleI> {

	public List<E> getAll();

}
