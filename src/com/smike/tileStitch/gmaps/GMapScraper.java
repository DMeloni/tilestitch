package com.smike.tileStitch.gmaps;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;

import com.smike.tileStitch.Scraper;
import com.smike.tileStitch.Tile;

public class GMapScraper extends Scraper {
	private static final Dimension TILE_SIZE = new Dimension(256, 256);
	
	public static final String GOOGLE_MAP_BASE_URL = "http://mt2.google.com/vt/lyrs=m@107&hl=en&";
	public static final String GOOGLE_LABEL_BASE_URL = "http://mt2.google.com/vt/lyrs=h@107&hl=en&";
	public static final String GOOGLE_SAT_BASE_URL = "http://khm2.google.com/kh/v=46&";
	
	public GMapScraper(String baseUrl) {
		super(baseUrl, TILE_SIZE);
	}

	protected String constructUrl(Tile tile) {
		return getBaseUrl() + "z=" + tile.getZoom() + "&x=" + tile.getCol() + "&y="
		+ tile.getRow();
	}

	protected String constructFileName(Tile tile) {
		return tile.getZoom() + "-" + tile.getCol() + "-" + tile.getRow()
		+ ".png";
	}
	
	public static void main(String[] args) {
		try {
//			File outputDir = new File("output/nyc_gmap");
//			Point startPoint = new Point(601, 768);
//			Dimension dimension = new Dimension(4, 4);
//			
//			GMapScraper mapGmapScraper = new GMapScraper(GOOGLE_MAP_BASE_URL);
//			mapGmapScraper.saveCompleteImage(11, startPoint, dimension, new File(outputDir, "map"));
//			
//			GMapScraper labelGmapScraper = new GMapScraper(GOOGLE_LABEL_BASE_URL);
//			mapGmapScraper.saveCompleteImage(11, startPoint, dimension, new File(outputDir, "label"));
//			
//			GMapScraper satGmapScraper = new GMapScraper(GOOGLE_SAT_BASE_URL);
//			satGmapScraper.saveCompleteImage(11, startPoint, dimension, new File(outputDir, "sat"));
			
			File outputDir = new File("output/nyc_gmap");
			Point startPoint = new Point(1205, 1536);
			Dimension dimension = new Dimension(5, 5);
			GMapScraper zipGmapScraper = new GMapScraper("http://ts3.usnaviguide.com/tileserver.pl?T=7cfb3c8797&") {
				protected String constructUrl(Tile tile) {
					return getBaseUrl() + "Z=" + tile.getZoom() + "&X=" + tile.getCol() + "&Y="
					+ tile.getRow();
				}
			};
			zipGmapScraper.saveCompleteImage(12, startPoint, dimension, new File(outputDir, "zip"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
