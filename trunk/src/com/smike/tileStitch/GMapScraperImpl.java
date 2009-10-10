package com.smike.tileStitch;

import java.awt.Dimension;


public class GMapScraperImpl extends Scraper {
	private static final Dimension TILE_SIZE = new Dimension(256, 256);
	
	public static final String GOOGLE_MAP_BASE_URL = "http://mt2.google.com/vt/lyrs=m@107&hl=en&";
	public static final String GOOGLE_LABEL_BASE_URL = "http://mt2.google.com/vt/lyrs=h@107&hl=en&";
	public static final String GOOGLE_SAT_BASE_URL = "http://khm2.google.com/kh/v=46&";
	
	public GMapScraperImpl(String baseUrl) {
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
}
