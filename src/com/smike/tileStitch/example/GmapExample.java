package com.smike.tileStitch.example;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;

import com.smike.tileStitch.GMapScraperImpl;

public class GmapExample {
	/**
	 * An example that shows how to scrape layers off of Google Maps.
	 */
	public static void main(String[] args) {
		try {
			File outputDir = new File("output/nyc_gmap");
			Point startPoint = new Point(601, 768);
			Dimension dimension = new Dimension(4, 4);
			
			// Get the map layer.
			GMapScraperImpl mapGmapScraper = new GMapScraperImpl(GMapScraperImpl.GOOGLE_MAP_BASE_URL);
			mapGmapScraper.saveCompleteImage(11, startPoint, dimension, new File(outputDir, "map"));
			
			// Get the label (streets, etc. overlay over satellite view) layer.
			GMapScraperImpl labelGmapScraper = new GMapScraperImpl(GMapScraperImpl.GOOGLE_LABEL_BASE_URL);
			labelGmapScraper.saveCompleteImage(11, startPoint, dimension, new File(outputDir, "label"));
			
			// Get the satellite layer.
			GMapScraperImpl satGmapScraper = new GMapScraperImpl(GMapScraperImpl.GOOGLE_SAT_BASE_URL);
			satGmapScraper.saveCompleteImage(11, startPoint, dimension, new File(outputDir, "sat"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
